package com.thinh.nt226.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.thinh.nt226.contactmanager.model.Contact;

public class NhanTinSmsActivity extends AppCompatActivity {
    TextView txtNguoiNhan;
    EditText txtNoiDung;
    ImageButton btnSend;

    Contact selectedContact = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_tin_sms);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTinNhan();
            }
        });
    }

    private void xuLyTinNhan() {
        final SmsManager sms = SmsManager.getDefault();
        Intent msgSent = new Intent("ACTION_MSG_SENT");
        //xử lý trả kết quả có thành công hay không
        final PendingIntent pendingMsgSent =
                PendingIntent.getBroadcast(NhanTinSmsActivity.this,0,msgSent,0);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg = "OK";
                if(result!= Activity.RESULT_OK)
                    msg = "Not OK";
                Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
            }
        },new IntentFilter("ACTION_MSG_SENT"));

        sms.sendTextMessage(
                selectedContact.getPhone(),
                null,
                txtNoiDung.getText().toString(),
                pendingMsgSent,
                null);
    }

    private void addControls() {
        txtNguoiNhan = (TextView) findViewById(R.id.txtNguoiNhan);
        txtNoiDung = (EditText) findViewById(R.id.txtNoiDung);
        btnSend = (ImageButton) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        selectedContact = (Contact) intent.getSerializableExtra("CONTACT");
        txtNguoiNhan.setText(selectedContact.getName()+"-"+selectedContact.getPhone());
    }
}