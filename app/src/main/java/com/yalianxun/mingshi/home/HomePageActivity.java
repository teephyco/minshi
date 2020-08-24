package com.yalianxun.mingshi.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.yalianxun.mingshi.BaseActivity;
import com.yalianxun.mingshi.LoginActivity;
import com.yalianxun.mingshi.R;
import com.yalianxun.mingshi.adapter.BannerPagerAdapter;
import com.yalianxun.mingshi.adapter.CustomRecyclerAdapter;
import com.yalianxun.mingshi.adapter.DevelopAdapter;
import com.yalianxun.mingshi.adapter.DoorAdapter;
import com.yalianxun.mingshi.adapter.LifeInfoRecyclerAdapter;
import com.yalianxun.mingshi.beans.Door;
import com.yalianxun.mingshi.beans.ImageInfo;
import com.yalianxun.mingshi.beans.Notification;
import com.yalianxun.mingshi.beans.UserInfo;
import com.yalianxun.mingshi.personal.PersonalActivity;
import com.yalianxun.mingshi.utils.JsonUtil;
import com.yalianxun.mingshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity {
    public static final int AUTO_SCROLL = 0;
    private ViewPager viewPager;
    private static final int FIRST_PAGE = 1;
    private ArrayList<ImageInfo> dataList  = new ArrayList<>();
    private ArrayList<ImageInfo> labelList  = new ArrayList<>();
    private int currentPosition;
    private List<String> locations = new ArrayList<>();
    private List<UserInfo> list;
    private UserInfo userInfo;
    private String CUSTOMER_SERVICE_PHONE = "075528716473";
    private TextView userLocation;
    private String [] labels = {"物业缴费","通知公告","我要报事","生活资讯","物业档案","客服电话"};
    private int [] label_icons = {R.drawable.fee_icon,R.drawable.noti_icon,R.drawable.report_icon,R.drawable.life_info_icon,R.drawable.document_icon,R.drawable.customer_icon};
    private int [] pictureURLS = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4};
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == AUTO_SCROLL) {
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                handler.sendEmptyMessageDelayed(AUTO_SCROLL, 2000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        List<Door> listViewData = new ArrayList<>();
        Door door = new Door("1231232412","东区",0);
        listViewData.add(door);
        door = new Door("123187678412","西区",1);
        listViewData.add(door);
        door = new Door("653233453313","写字楼A区",1);
        listViewData.add(door);
        door = new Door("653233773313","地下车库",1);
        listViewData.add(door);
        DoorAdapter adapter = new DoorAdapter(listViewData,this);
        ListView listView = findViewById(R.id.shortcut_open_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Door selectedDoor = listViewData.get(position);
            if (selectedDoor.getStatus() == 0){
                ToastUtils.showTextToast(HomePageActivity.this,"设备离线");
            }else {
                openDoorStatus();
//                ToastUtils.showTextToast(HomePageActivity.this,"开门成功");
            }
        });

        //banner
        initData();
        viewPager = findViewById(R.id.view_pager);
        BannerPagerAdapter pagerAdapter = new BannerPagerAdapter(this, dataList,handler);
        pagerAdapter.setListener(value -> {

        });
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(FIRST_PAGE);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE)
                    viewPager.setCurrentItem(currentPosition, false);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == dataList.size() - 1) {
                    currentPosition = FIRST_PAGE;
                } else if (position == 0) {
                    currentPosition = dataList.size() - 2;
                } else {
                    currentPosition = position;
                }
            }

        });
        handler.sendEmptyMessageDelayed(AUTO_SCROLL, 2000);

        //get data
        String response = SP_MANAGER.getValue("loginResponse","");
        list = JsonUtil.getJsonUtil().getUserInfoList(response);
        for(UserInfo userInfo : list){
            String location = userInfo.getProjectName() + userInfo.getBuildingName() + userInfo.getHouseNum();
            locations.add(location);
        }
        userLocation = findViewById(R.id.user_location);
        userLocation.setText(locations.get(0));
        userInfo = list.get(0);
        saveData();

        //recycle view
        RecyclerView recyclerView = findViewById(R.id.recycler);
        CustomRecyclerAdapter customRecyclerAdapter = new CustomRecyclerAdapter(labelList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomePageActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        customRecyclerAdapter.setOnItemClickListener((v, position) -> goToNextActivity(position));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customRecyclerAdapter);


        ArrayList<Notification> messages = new ArrayList<>();
//        String title, String timestamp, int countNum, int resID, int notificationType
        Notification notification = new Notification("蜂蜜的功效","",200,R.drawable.honey,1);
        notification.setSourceLink("https://baijiahao.baidu.com/s?id=1612532540849539614&wfr=spider&for=pc");
        messages.add(notification);
        notification = new Notification("茶道的艺术","",300,R.drawable.tea,1);
        notification.setSourceLink("https://baijiahao.baidu.com/s?id=1595700515789334333&wfr=spider&for=pc");
        messages.add(notification);
        RecyclerView info_recycler = findViewById(R.id.life_info_recycler);
        LifeInfoRecyclerAdapter lifeInfoRecyclerAdapter = new LifeInfoRecyclerAdapter(messages,this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(HomePageActivity.this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        lifeInfoRecyclerAdapter.setOnItemClickListener((v, position) -> {
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra("key","life");
            intent.putExtra("url",messages.get(position).getSourceLink());
            startActivity(intent);
        });

        info_recycler.setLayoutManager(linearLayoutManager1);
        info_recycler.setAdapter(lifeInfoRecyclerAdapter);

        //property development

        ListView develop_lv = findViewById(R.id.home_lv);
        List<Notification> developList = new ArrayList<>();
        Notification notify = new Notification("小区绿化环境进行进一步整改","小区园林绿化的建设规范指标 住宅园林设计上强调以“绿”为主,园林绿化生态效益的发挥,主要由树木、花草的种植来实现,因此,以“绿”为本。为进一步改善小区生活环境，","刚刚",121,"https://pics6.baidu.com/feed/5243fbf2b2119313350a7141b34343d190238d7c.jpeg?token=3f2a65790e7583d7757328455ff05171&s=A4AA47B04A5057DE0EA9ECB603001010");
        notify.setResID(R.drawable.property_home);
        notify.setSourceLink("file:///android_asset/property_dev1.html");
        developList.add(notify);
        notify = new Notification("组织党员学习全面从严治党","6月1日下午，物业管理中心党支部在中心319培训室，组织全体党员进行了集中学习，学习活动由党支部书记主持。学习内容为 2017年10月第1版《中国共产党章程》和学校召开全面从严治党","07月23日",77,"");
        notify.setResID(R.drawable.property_home_other);
        notify.setSourceLink("file:///android_asset/property_dev2.html");
        developList.add(notify);
        DevelopAdapter developAdapter = new DevelopAdapter(developList,this);
        develop_lv.setAdapter(developAdapter);
        develop_lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra("key","life");
            intent.putExtra("url",developList.get(position).getSourceLink());
            startActivity(intent);
        });

    }

    private void initData(){
        dataList.add(new ImageInfo(R.drawable.banner4));
        for(int resID : pictureURLS){
            dataList.add(new ImageInfo(resID));
        }
        dataList.add(new ImageInfo(R.drawable.banner1));
        for(int i = 0;i < labels.length; i++){
            labelList.add(new ImageInfo(labels[i],label_icons[i]));
        }

    }

    private void goToNextActivity(int position){
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent = new Intent(this, PaymentActivity.class);
                break;
            case 1:
                intent = new Intent(this, PropertyNotifyActivity.class);
                break;
            case 2:
                intent = new Intent(this, ReportEventActivity.class);
                break;

            case 3:
                intent = new Intent(this, LifeInfoActivity.class);
                break;
            case 4:
                intent = new Intent(this, PropertyDocumentActivity.class);
                break;

            case 5:
                if(CUSTOMER_SERVICE_PHONE.equals("")){
                    ToastUtils.showTextToast(this,"小区暂无客服电话");
                }else {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+CUSTOMER_SERVICE_PHONE));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                break;
            default:
                break;

        }
        if(position != 5){
            intent.putExtra("userInfo",userInfo);
        }
        startActivity(intent);
    }

    public void goToPersonalCenter(View view) {
        startActivityForResult(new Intent(this, PersonalActivity.class),1000);
    }

    public void hideOpenDoor(View view) {
        findViewById(R.id.open_door_view).setVisibility(View.GONE);
    }

    public void showOpenDoor(View view) {
        findViewById(R.id.open_door_view).setVisibility(View.VISIBLE);
    }

    public void doNothing(View view) {
    }

    private void saveData(){
        SP_MANAGER.putValue("location",userInfo.getProjectName() + userInfo.getBuildingName() + userInfo.getHouseNum());
        SP_MANAGER.putValue("projectName",userInfo.getProjectName());
        SP_MANAGER.putValue("buildingName",userInfo.getBuildingName());
        SP_MANAGER.putValue("houseNum",userInfo.getHouseNum());
        SP_MANAGER.putValue("houseType",userInfo.getHouseType());
        CUSTOMER_SERVICE_PHONE = userInfo.getServiceTel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public void changeLocation(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1010){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void hideOpenDoorStatus(View view) {
        findViewById(R.id.open_door_status).setVisibility(View.GONE);
    }

    private void openDoorStatus(){
        findViewById(R.id.open_door_view).setVisibility(View.GONE);
        TextView textView = findViewById(R.id.close_status);
        textView.setText("开门成功！");
        findViewById(R.id.open_door_status).setVisibility(View.VISIBLE);
    }
}
