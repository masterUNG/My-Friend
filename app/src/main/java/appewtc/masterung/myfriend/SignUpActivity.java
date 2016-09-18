package appewtc.masterung.myfriend;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText,
            passwordEditText, rePasswordEditText;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private ImageView imageView;
    private String nameString, userString, passwordString,
            rePasswordString, sexString, imageString,
            imagePathString, imageNameString;
    private boolean statusABoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        rePasswordEditText = (EditText) findViewById(R.id.editText4);
        radioGroup = (RadioGroup) findViewById(R.id.ragSex);
        maleRadioButton = (RadioButton) findViewById(R.id.radioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.radioButton2);
        imageView = (ImageView) findViewById(R.id.imageView);

        //Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,
                        "โปรดเลือกรูปภาพ"), 1);

            }   // onClick
        });

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButton:
                        sexString = "Male";
                        break;
                    case R.id.radioButton2:
                        sexString = "Female";
                        break;
                }   // switch

            }   // onChecked
        });

    }   // Main Method

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            // Result Complete
            Log.d("MyFriendV1", "Result ==> OK");

            //Find Path of Image
            Uri uri = data.getData();
            imagePathString = myFindPathImage(uri);
            Log.d("MyFriendV1", "imagePathString ==> " + imagePathString);

            //Setup Image to ImageView
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }   // try

            statusABoolean = false;


        }   // if

    }   // onActivityResult

    private String myFindPathImage(Uri uri) {

        String strResult = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings,
                null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int intIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            strResult = cursor.getString(intIndex);

        } else {
            strResult = uri.getPath();
        }


        return strResult;
    }

    public void clickSignUpSign(View view) {

        //Get Value From Edit Text
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        rePasswordString = rePasswordEditText.getText().toString().trim();

        //Check Space
        if (nameString.equals("") || userString.equals("") ||
                passwordString.equals("") || rePasswordString.equals("")) {
            //Have Space
            MyAlert myAlert = new MyAlert(this,
                    R.drawable.doremon48, "มีช่องว่าง", "กรุณากรอกทุกช่องคะ");
            myAlert.myDialog();
        } else if (!passwordString.equals(rePasswordString)) {
            // Password not Match
            MyAlert myAlert = new MyAlert(this, R.drawable.nobita48,
                    "Password ผิด", "กรุณาพิมพ์ Password ให้เหมือนกัน");
            myAlert.myDialog();
        } else if (!(maleRadioButton.isChecked() || femaleRadioButton.isChecked())) {
            // Non Choose Sex
            MyAlert myAlert = new MyAlert(this, R.drawable.bird48,
                    "ยังไม่เลือก เพศ", "กรุณาเลือกเพศด้วยคะ");
            myAlert.myDialog();
        } else if (statusABoolean) {
            MyAlert myAlert = new MyAlert(this, R.drawable.kon48,
                    "ยังไม่เลือกรูป", "กรุณาเลือกรูป ด้วยคะ");
            myAlert.myDialog();
        } else {
            // Upload Image and Data to Server
            uploadImageToServer();
            insertDataToServer();

        }


    }   // clickSign

    private void insertDataToServer() {

        imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
        imageNameString = "http://swiftcodingthai.com/18Sep/Image" + imageNameString;
        Log.d("MyFriendV1", "imageNameString ==> " + imageNameString);



    }   // insertData

    private void uploadImageToServer() {

        //New Policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21,
                    "18Sep@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image");
            simpleFTP.stor(new File(imagePathString));
            simpleFTP.disconnect();

            Log.d("MyFriendV1", "Upload Finish");

        } catch (Exception e) {
            Log.d("MyFriendV1", "e ==> " + e.toString());
        }

    }   // uploadImageToServer


}   // Main Class
