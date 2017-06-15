package com.bignerdranch.android.minimal;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.minimal.data.TodoItem;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class AddTodoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener ,
        TimePickerDialog.OnTimeSetListener{

    private static final String TAG = "AddTodoActivity";
    private EditText mTodoTitle;
    private SwitchCompat mToDoDateSwitch;
    private TextView mReminderDate;
    private EditText mDatePick;
    private EditText mTimePick;
    private FloatingActionButton mToDoSendFAB;
    private TodoItem mUserToDoItem;
    private String mUserTodoTitle;
    private boolean mUserIsReminder;
    private Date mUserReminderDate;
    private int mUserColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        setTolbarConfig();


        mUserToDoItem = (TodoItem)getIntent().getSerializableExtra("TODOITEM");

        initView();
        initData();

        mTodoTitle.setText(mUserToDoItem.getmTodoText());
    }


    private void setTolbarConfig() {

        Drawable corss = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_white_24dp, null);

//        if (corss != null) {
//            corss.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.DST_ATOP);
//        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(corss);
        }
    }


    /**
     * 初始化控件
     */
    private void initView() {
        //提醒内容
        mTodoTitle = (EditText) findViewById(R.id.todo_title);
        //是否设置提醒时间
        mToDoDateSwitch = (SwitchCompat)findViewById(R.id.toDoHasDateSwitchCompat);
        //选择提醒日期
        mDatePick = (EditText)findViewById(R.id.todo_date_pick);
        //选择提醒时间
        mTimePick = (EditText)findViewById(R.id.todo_time_pick);
        //保存按钮
        mToDoSendFAB = (FloatingActionButton)findViewById(R.id.makeToDoFloatingActionButton);
        //显示提醒时间
        mReminderDate = (TextView)findViewById(R.id.newToDoDateTimeReminderTextView);
    }

    /**
     * 设置数据
     */
    private void initData() {
        //标题
        mUserTodoTitle = mUserToDoItem.getmTodoText();
        //是否设置提醒
        mUserIsReminder = mUserToDoItem.ismIsReminder();
        //时间
        mUserReminderDate = mUserToDoItem.getmTodoDate();
        //颜色
        mUserColor = mUserToDoItem.getmTodoColor();


        if (mUserIsReminder) {

            if (mUserReminderDate != null) {

                Toast.makeText(this, mUserReminderDate.toString(), Toast.LENGTH_SHORT).show();
                //设置时间显示
                setReminderDate();
            } else {

                //开关关闭
                mToDoDateSwitch.setChecked(false);
                //时间控件隐藏
                mReminderDate.setVisibility(View.INVISIBLE);

            }
        }
        //设置Title
        mTodoTitle.requestFocus();
        mTodoTitle.setText(mUserTodoTitle);

        mTodoTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //获取最新的title
                mUserTodoTitle = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mToDoDateSwitch.setChecked(mUserIsReminder);

        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {


                } else {
                    //提醒时间设置为0
                    mUserReminderDate = null;
                }

                mUserIsReminder = isChecked;

                setDateAndTimeEditText();
            }
        });

        mDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                if (mUserToDoItem.getmTodoDate() != null) {
                    date = mUserReminderDate;
                } else{

                    date = new Date();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddTodoActivity.this,
                                year, month, day);

                datePickerDialog.show(getFragmentManager(), "DateFragment");

                //DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoActivity.this, year, month, day);

            }
        });

        mTimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;

                if(mUserToDoItem.getmTodoDate()!=null){
//                    date = mUserToDoItem.getToDoDate();
                    date = mUserReminderDate;
                }
                else{
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddTodoActivity.this, hour, minute, DateFormat.is24HourFormat(AddTodoActivity.this));

                timePickerDialog.show(getFragmentManager(), "TimeFragment");
            }
        });

        setDateAndTimeEditText();

    }

    private void setDateAndTimeEditText(){

        if(mUserToDoItem.ismIsReminder() && mUserReminderDate!=null){
            String userDate = formatDate("d MMM, yyyy", mUserReminderDate);
            String formatToUse;
            if(DateFormat.is24HourFormat(this)){
                formatToUse = "k:mm";
            }
            else{
                formatToUse = "h:mm a";

            }
            String userTime = formatDate(formatToUse, mUserReminderDate);

            Log.i(TAG,userTime);
            Log.i(TAG,userDate);
            mTimePick.setText(userTime);
            mDatePick.setText(userDate);

        }
        else{
            mDatePick.setText(getString(R.string.date_reminder_default));
//            mUserReminderDate = new Date();
            boolean time24 = DateFormat.is24HourFormat(this);
            Calendar cal = Calendar.getInstance();
            if(time24){
                cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+1);
            }
            else{
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+1);
            }
            cal.set(Calendar.MINUTE, 0);
            mUserReminderDate = cal.getTime();
            Log.d("OskarSchindler", "Imagined Date: "+mUserReminderDate);
            String timeString;
            if(time24){
                timeString = formatDate("k:mm", mUserReminderDate);
            }
            else{
                timeString = formatDate("h:mm a", mUserReminderDate);
            }
            mTimePick.setText(timeString);

        }
    }


    /**
     * 设置时间显示
     */
    private void setReminderDate() {

        if (mUserReminderDate != null) {

            mReminderDate.setVisibility(View.VISIBLE);

            if (mUserReminderDate.before(new Date())) {

                mReminderDate.setText(getString(R.string.date_error_check_again));
                mReminderDate.setTextColor(Color.RED);
                return;
            }

            Date date = mUserReminderDate;

            String dateString = formatDate("d MM, yyyy", date);

            String timeString;
            String amPmString = "";

            if (DateFormat.is24HourFormat(this)) {
                timeString = formatDate("k:mm",date);
            } else {

                timeString = formatDate("h:mm", date);
                amPmString = formatDate("a", date);
            }
            String finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);

            mReminderDate.setTextColor(getResources().getColor(R.color.secondary_text));
            mReminderDate.setText(finalString);
        }


    }

    public static String formatDate(String formatString, Date dateToFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(dateToFormat);
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i1, int i2) {

    }
}
