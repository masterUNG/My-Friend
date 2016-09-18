package appewtc.masterung.myfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText,
            passwordEditText, rePasswordEditText;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private ImageView imageView;
    private String nameString, userString, passwordString,
            rePasswordString, sexString, imageString;


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


    }   // Main Method

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            // Result Complete
            Log.d("MyFriendV1", "Result ==> OK");

        }   // if

    }   // onActivityResult

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
        }


    }   // clickSign


}   // Main Class
