//package com.tranvu1805.warehousemanager;
//
//import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.tranvu1805.warehousemanager.DAO.InvoiceDetailDAO;
//import com.tranvu1805.warehousemanager.databinding.ActivityInvoiceStatiscalBinding;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//public class InvoiceStatiscal extends AppCompatActivity {
//    ActivityInvoiceStatiscalBinding binding;
//    InvoiceDetailDAO invoiceDetailDAO;
//    ArrayList<String> listStatic;
//    ArrayAdapter<String> spAdapter;
//
//    @SuppressLint({"SetTextI18n", "NewApi"})
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityInvoiceStatiscalBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        invoiceDetailDAO = new InvoiceDetailDAO(this);
//        listStatic = new ArrayList<>();
//        listStatic.add("Tổng tiền nhập kho");
//        listStatic.add("Tổng tiền xuất kho");
//        spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listStatic);
//        binding.spType.setAdapter(spAdapter);
//        binding.btnDate.setOnClickListener(v -> {
//            binding.containLayout.setVisibility(View.INVISIBLE);
//            binding.txtMoney.setVisibility(View.VISIBLE);
//            if (binding.spType.getSelectedItem().toString().equals("Tổng tiền nhập kho")) {
//                binding.txtMoney.setText("Tổng tiền nhập kho trong ngày là: "
//                        + invoiceDetailDAO.getSumMoney(getDateNow(), getDateNow(), 0));
//            } else {
//                binding.txtMoney.setText("Tổng tiền xuất kho trong ngày là: "
//                        + invoiceDetailDAO.getSumMoney(getDateNow(), getDateNow(), 1));
//            }
//        });
//        binding.btnMonth.setOnClickListener(v -> {
//            binding.containLayout.setVisibility(View.INVISIBLE);
//            binding.txtMoney.setVisibility(View.VISIBLE);
//            if (binding.spType.getSelectedItem().toString().equals("Tổng tiền nhập kho")) {
//                binding.txtMoney.setText("Tổng tiền nhập kho trong tháng là: "
//                        + invoiceDetailDAO.getSumMoney(getFirstDayOfMonth(), getLastDayOfMonth(), 0));
//            } else {
//                binding.txtMoney.setText("Tổng tiền xuất kho trong tháng là: "
//                        + invoiceDetailDAO.getSumMoney(getFirstDayOfMonth(), getLastDayOfMonth(), 1));
//            }
//        });
//        binding.btnYear.setOnClickListener(v -> {
//            binding.containLayout.setVisibility(View.INVISIBLE);
//            binding.txtMoney.setVisibility(View.VISIBLE);
//            if (binding.spType.getSelectedItem().toString().equals("Tổng tiền nhập kho")) {
//                binding.txtMoney.setText("Tổng tiền nhập kho trong năm là: "
//                        + invoiceDetailDAO.getSumMoney(getFirstDayOfYear(), getDateNow(), 0));
//            } else {
//                binding.txtMoney.setText("Tổng tiền xuất kho trong năm là: "
//                        + invoiceDetailDAO.getSumMoney(getFirstDayOfYear(), getDateNow(), 1));
//            }
//        });
//        binding.btnCustom.setOnClickListener(v -> {
//            binding.containLayout.setVisibility(View.VISIBLE);
//            setEdtDateNow(binding.edtDateStart);
//            setEdtDateNow(binding.edtDateEnd);
//            String dateStart = binding.edtDateStart.getText().toString();
//            String dateEnd = binding.edtDateEnd.getText().toString();
//            if (binding.spType.getSelectedItem().toString().equals("Tổng tiền nhập kho")) {
//                if (dateStart.isEmpty() && dateEnd.isEmpty()) {
//
//                } else {
//                    binding.btnCheck.setOnClickListener(v1 -> {
//                        binding.txtMoney.setVisibility(View.VISIBLE);
//                        binding.txtMoney.setText("Tổng tiền nhập kho trong khoảng thời gian đã chọn là: "
//                                + invoiceDetailDAO.getSumMoney(binding.edtDateStart.getText().toString(),
//                                binding.edtDateEnd.getText().toString(), 0));
//                    });
//                }
//            } else {
//                if (dateStart.isEmpty() && dateEnd.isEmpty()) {
//
//                } else {
//                    binding.btnCheck.setOnClickListener(v1 -> {
//                        binding.txtMoney.setVisibility(View.VISIBLE);
//                        binding.txtMoney.setText("Tổng tiền nhập kho trong khoảng thời gian đã chọn là: "
//                                + invoiceDetailDAO.getSumMoney(binding.edtDateStart.getText().toString(),
//                                binding.edtDateEnd.getText().toString(), 1));
//                    });
//                }
//            }
//        });
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    private String getDateNow() {
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        return dateFormat.format(date);
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    private String getFirstDayOfYear() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        calendar.set(year, 1, 1);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        return dateFormat.format(calendar.getTime());
//    }
//
//    @SuppressLint({"NewApi", "SimpleDateFormat"})
//    private String getFirstDayOfMonth() {
//        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        return dateFormat.format(java.sql.Date.valueOf(String.valueOf(firstDay)));
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    private String getLastDayOfMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Date lastDayOfMonth = calendar.getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        return dateFormat.format(lastDayOfMonth);
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    private void setEdtDateNow(EditText edt) {
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        String dateString = dateFormat.format(date);
//        edt.setText(dateString);
//        edt.setOnClickListener(v -> {
//            Calendar calendar = Calendar.getInstance();
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DATE);
//            DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
//                calendar.set(i, i1, i2);
//                edt.setText(dateFormat.format(calendar.getTime()));
//            }, year, month, day);
//            dialog.show();
//        });
//    }
//}