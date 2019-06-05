package com.zlj.createforum.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zlj.createforum.R;
import com.zlj.createforum.ui.CourierActivity;
import com.zlj.createforum.ui.LoginActivity;
import com.zlj.createforum.ui.PhoneActivity;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.ShareUtils;
import com.zlj.createforum.utils.StaticClass;
import com.zlj.createforum.utils.UtilTools;
import com.zlj.createforum.view.CustomDialog;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.fragment
 * 文件名:   UserFragment
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/9 15:38
 * 描述:    个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_logout;
//    private TextView edit_user;
    private EditText show_name, show_sex, show_age;
    private Button update_data;
    //圆形头像
    private CircleImageView profile_image;
    private CustomDialog dialog;
    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;
//    private TextView tv_courier;
//    private TextView tv_phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
//        edit_user = (TextView) view.findViewById(R.id.edit_user);
        show_name = (EditText) view.findViewById(R.id.show_name);
        show_sex = (EditText) view.findViewById(R.id.show_sex);
        show_age = (EditText) view.findViewById(R.id.show_age);
//        show_desc = (EditText) view.findViewById(R.id.show_desc);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        update_data = (Button) view.findViewById(R.id.update_data);
//        tv_courier = (TextView) view.findViewById(R.id.tv_courier);
//        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
//        edit_user.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        update_data.setOnClickListener(this);
//        tv_courier.setOnClickListener(this);
//        tv_phone.setOnClickListener(this);

        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        //默认是不可点击,不可输入的
        setEnable(false);

        //设置具体的值
//        MyUser userInfo =  BmobUser.getCurrentUser(MyUser.class);
        SharedPreferences sp = this.getActivity().getSharedPreferences(ShareUtils.NAME, Context.MODE_PRIVATE);
        show_name.setText(sp.getString("username", ""));
        show_sex.setText(sp.getString("tags", ""));
        show_age.setText(sp.getString("nickname", ""));
        String avatar = sp.getString("avatar", "");
//        show_desc.setText(sp.getString("desc", "这人很懒，什么也没有留下"));

        //加载头像
        if (avatar.length() <= 0) {
            avatar = StaticClass.default_avatar;
        }
        UtilTools.getImageFromUrl(avatar, profile_image);

        //初始化dialog
        dialog = new CustomDialog(getActivity(), WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.MATCH_PARENT, R.layout.dialog_photo,
                R.style.pop_anim_style, Gravity.BOTTOM, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    //设置是否可编辑
    private void setEnable(boolean is) {
        show_name.setEnabled(is);
        show_age.setEnabled(is);
//        show_desc.setEnabled(is);
        show_sex.setEnabled(is);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.tv_phone:
//                startActivity(new Intent(getActivity(), PhoneActivity.class));
//                break;
//            case R.id.tv_courier:
//                startActivity(new Intent(getActivity(), CourierActivity.class));
//                break;
            case R.id.btn_logout:
                //退出登录
                //清除缓存的对象
//                MyUser.logOut();
//                // 现在的currentUser是null了
//                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑资料
//            case R.id.edit_user:
//                update_data.setVisibility(View.VISIBLE);
//                setEnable(true);
//                break;
            //确认修改
//            case R.id.update_data:
//                //拿到输入框的值
//                String name = show_name.getText().toString().trim();
//                String desc = show_desc.getText().toString().trim();
//                String age = show_age.getText().toString().trim();
//                String sex = show_sex.getText().toString().trim();

                //判断是否为空
//                if (!TextUtils.isEmpty(name)) {
                    //更新数据
//                    MyUser myUser = new MyUser();
//                    myUser.setUsername(name);
//                    myUser.setAge(Integer.parseInt(age));
//                    if (sex.equals("男")) {
//                        myUser.setSex(true);
//                    } else if (sex.equals("女")) {
//                        myUser.setSex(false);
//                    }
//                    if (!TextUtils.isEmpty(desc)) {
//                        myUser.setDesc(desc);
//                    } else {
//                        myUser.setDesc("这个人很懒，什么都没有留下");
//                    }
//                    BmobUser bmobUser = BmobUser.getCurrentUser();
//                    myUser.update(bmobUser.getObjectId(), new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if(e == null) {
//                                //修改成功
//                                setEnable(false);
//                                update_data.setVisibility(View.GONE);
//                                Toast.makeText(getActivity(), "修改成功",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getActivity(), "修改失败",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                } else {
//                    Toast.makeText(getActivity(), R.string.edit_view_null_error,
//                            Toast.LENGTH_SHORT).show();
//                }
//                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 444;
    public static final int IMAGE_REQUEST_CODE = 666;
    public static final int RESULT_REQUEST_CODE = 999;
    private File tempFile = null;
    private Uri imageUri;

    //跳转相机
    private void toCamera() {
        File outputImage = new File(getActivity().getExternalCacheDir(),
                PHOTO_IMAGE_FILE_NAME);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //用于无法加载图片的错误
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getActivity(),
                    "com.zlj.zljapp.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用,可用就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //返回的相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //返回的相机数据
                case CAMERA_REQUEST_CODE:
                    //tempFile = new File(Environment.getDataDirectory(), PHOTO_IMAGE_FILE_NAME);
                    //startPhotoZoom(Uri.fromFile(tempFile));
                    startPhotoZoom(imageUri);
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        UtilTools.putImageToShare(getActivity(), profile_image);
                        //设置图片后删除原来的
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri==null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪框高
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    private void setImageToView(Intent data) {
        //用于携带数据，相当于map
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }
}
