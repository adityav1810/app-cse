package com.adityavarshney.bennettmedica;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;


public class UploadScan extends AppCompatActivity implements View.OnClickListener {

    private static int load_img = 1;
    private TextView imgpath;
    Intent myFileIntent;
    Uri myUri;


    private final int IMG_REQUEST = 1;
    private int STORAGE_PERMISSION_CODE = 1;
    private Button UploadBn, ChooseBn;
    private ImageView imgView;
    private EditText NAME;
    private Bitmap bitmap;
    public String filePath;
    private String UploadUrl = "http://10.12.143.251:8080";

    //Choose Image
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_scan);


        UploadBn = (Button) findViewById(R.id.img_choose);
        ChooseBn = (Button) findViewById(R.id.button);
        NAME = (EditText) findViewById(R.id.img_name);
        imgView = (ImageView) findViewById(R.id.img_preview);
        ChooseBn.setOnClickListener(this);
        UploadBn.setOnClickListener(this);
        imgpath = (TextView) findViewById(R.id.img_path);

        if (ContextCompat.checkSelfPermission(UploadScan.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(UploadScan.this, "You Have Already Granted this Permission", Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }

        ChooseBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent, 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        switch (requestCode) {

            case 10:
                if (resultCode == RESULT_OK) {

                    filePath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

                    imgpath.setText(filePath);
                    ///////filePath gets path of image as String

                }
                break;


        }


    }

    //Permissions
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Permissions required to access Gallery")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(UploadScan.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show().create();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                selectImg();
                break;
            case R.id.img_choose:
                uploadImg();
                break;
        }

    }

    private void selectImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }





    private void uploadImg() {

//        Uploader uploadimg=new Uploader();
//        uploadimg.updatePicture(filePath);
        openWebURL();


    }


    public void openWebURL() {
        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( UploadUrl ) );

        startActivity( browse );
    }

}

class Uploader extends UploadScan{
    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            filePath = f.getParent();
            System.out.println(""+filePath);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

//    private Connection connect() {
//        // SQLite connection string
//        String url = "jdbc:sqlite:storage.db";
//        Connection conn = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            Properties config = new Properties();
//            config.setProperty("readOnly","false");
//            config.setProperty("open_mode", "1");
//            config.setProperty("mode", "rw");
//            // TODO: can't open db (read-only file system) even though directory has a+rw permissions
//            conn = DriverManager.getConnection(url);
//            System.out.println("connection estabilised"+conn);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }

//    public void updatePicture(String filename) {
//        // update sql
//        String updateSQL = "UPDATE images "
//                + "SET data = ? "
//                + "WHERE id=?";
//
//        try (Connection conn = connect();
//             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
//
//            // set parameters
//            pstmt.setBytes(2, readFile(filename));
////            pstmt.setInt(1, materialId);
//
//            pstmt.executeUpdate();
//            System.out.println("Stored the file in the BLOB column.");
//
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public void readPicture(int materialId, String filename) {
//        // update sql
//        String selectSQL = "SELECT data FROM images WHERE id=?";
//        ResultSet rs = null;
//        FileOutputStream fos = null;
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            conn = connect();
//            pstmt = conn.prepareStatement(selectSQL);
//            pstmt.setInt(1, materialId);
//            rs = pstmt.executeQuery();
//
//            // write binary stream into file
//            File file = new File(filename);
//            fos = new FileOutputStream(file);
//
//            System.out.println("Writing BLOB to file " + file.getAbsolutePath());
//            while (rs.next()) {
//                InputStream input = rs.getBinaryStream("data");
//                byte[] buffer = new byte[1024];
//                while (input.read(buffer) > 0) {
//                    fos.write(buffer);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//
//                if (conn != null) {
//                    conn.close();
//                }
//                if (fos != null) {
//                    fos.close();
//                }
//
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            } catch (java.sql.SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
