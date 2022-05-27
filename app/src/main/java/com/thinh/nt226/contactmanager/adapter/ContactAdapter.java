package com.thinh.nt226.contactmanager.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.thinh.nt226.contactmanager.NhanTinSmsActivity;
import com.thinh.nt226.contactmanager.R;
import com.thinh.nt226.contactmanager.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    Activity context;
    int resource;
    List<Contact> objects;

    public ContactAdapter(@NonNull Activity context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.item, null);
        TextView txtTen, txtPhone;
        ImageButton btnCall, btnSms, btnDelete;

        txtTen = row.findViewById(R.id.txtTen);
        txtPhone = row.findViewById(R.id.txtPhone);
        btnCall = row.findViewById(R.id.btnCall);
        btnSms = row.findViewById(R.id.btnSms);
        btnDelete = row.findViewById(R.id.btnDelete);

        Contact contact = objects.get(position);
        txtTen.setText(contact.getName());
        txtPhone.setText(contact.getPhone());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xyLyCall(contact);
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xyLySms(contact);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xyLyXoa(contact);
            }
        });
        return row;
    }

    private void xyLyXoa(Contact contact) {
        this.remove(contact);
    }

    private void xyLySms(Contact contact) {
        Intent intent = new Intent(this.context, NhanTinSmsActivity.class);
        intent.putExtra("CONTACT", contact);
        this.context.startActivity(intent);
    }

    private void xyLyCall(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + contact.getPhone());
        intent.setData(uri);
        if (ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.context.startActivity(intent);
    }
}
