package xmart.com.xmart_android.activity.user.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import xmart.com.xmart_android.R;
import xmart.com.xmart_android.db.NguoiDung;
import xmart.com.xmart_android.logging.L;
import xmart.com.xmart_android.service.NguoiDungService;

public class InformationUserActivity extends AppCompatActivity {

    private ImageView imageUser;
    private TextView userName;
    private TextView firstName;
    private TextView lastName;
    private TextView phoneNumber;
    private TextView birthday;
    private TextView gender;
    private TextView homeAddress;
    private TextView workAddress;
    private TextView point;
    private FloatingActionButton edit;

    private Toolbar toolbar;

    private NguoiDungService nguoiDungService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user);
        toolbar = (Toolbar) findViewById(R.id.app_bar_infor);
        setSupportActionBar(toolbar);
        setTitle("Information user");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nguoiDungService = new NguoiDungService(getApplicationContext());
        mapping();

        ArrayList<NguoiDung> arrayList = nguoiDungService.selectAllNguoiDung();
        NguoiDung nguoiDung = arrayList.get(0);
        //set du lieu
        //get image from url
        String url=nguoiDung.getImage().replace("../","http://xapp.codew.net/");
        new  DownloadImageTask(imageUser).execute(url);
        userName.setText(nguoiDung.getUserName());
        firstName.setText(nguoiDung.getFirstName());
        lastName.setText(nguoiDung.getLastName());
        phoneNumber.setText(nguoiDung.getPhoneNumber());
        birthday.setText(nguoiDung.getBirthday());
        gender.setText("0".equals(nguoiDung.getGender()) ? "Male" : "Female");
        homeAddress.setText(nguoiDung.getHomeAddr());
        workAddress.setText(nguoiDung.getWorkAddr());
        point.setText(nguoiDung.getPoint());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.t(getApplicationContext(),"Chức năng đang làm");
            }
        });

    }

    //class set image
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }

    //tao menu cho menu main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.infor_menu, menu);
        return true;
    }

    //xu ly su kien cho menu main
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_home) {
//            startActivity(new Intent(InformationUserActivity.this, MainActivity.class));
//            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
//        }

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_top, R.anim.slide_bottom);
        }
        return true;
    }


    public void mapping() {
        imageUser = (ImageView) findViewById(R.id.image_user);
        userName = (TextView) findViewById(R.id.userName);
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        birthday = (TextView) findViewById(R.id.birthday);
        gender = (TextView) findViewById(R.id.gender);
        homeAddress = (TextView) findViewById(R.id.homeAddress);
        workAddress = (TextView) findViewById(R.id.workAddress);
        point = (TextView) findViewById(R.id.point);

        edit = (FloatingActionButton) findViewById(R.id.edit_user);

    }
}
