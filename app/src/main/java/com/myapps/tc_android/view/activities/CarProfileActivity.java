package com.myapps.tc_android.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myapps.tc_android.R;
import com.myapps.tc_android.service.model.Car;
import com.myapps.tc_android.service.model.UserHolder;
import com.myapps.tc_android.service.repository.ApiRepository;
import com.myapps.tc_android.service.repository.ApiService;
import com.myapps.tc_android.viewmodel.CarViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CarProfileActivity extends AppCompatActivity {

    Car car;
    int carId;
    @BindView(R.id.textview_carprofile_year)
    TextView textviewCarprofileYear;
    @BindView(R.id.textview_carprofile_name)
    TextView textviewCarprofileName;
    @BindView(R.id.textview_carprofile_factory)
    TextView textviewCarprofileFactory;
    @BindView(R.id.textview_carprofile_kilometer)
    TextView textviewCarprofileKilometer;
    @BindView(R.id.textview_carprofile_cost)
    TextView textviewCarprofileCost;
    @BindView(R.id.textview_carprofile_color)
    TextView textviewCarprofileColor;
    @BindView(R.id.textview_carprofile_description)
    TextView textviewCarprofileDescription;
    @BindView(R.id.button_edit_carprofile)
    ImageButton buttonEditCarprofile;
    @BindView(R.id.button_delete_car_profile)
    ImageButton buttonDeleteCarProfile;
    @BindView(R.id.imageview_car_logo)
    CircleImageView imageviewCarLogo;
    @BindView(R.id.button_reserve)
    Button buttonReserve;
    private CarViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_profile);
        ButterKnife.bind(this);
        Intent i = getIntent();
        carId = i.getIntExtra("carId", -1);
        viewModel = ViewModelProviders.of(this).get(CarViewModel.class);
        obserViewModel();
        viewModel.getCar(carId);
        if (UserHolder.Instance().getUser().getRole().equals("admin") == false) {
            buttonEditCarprofile.setVisibility(View.GONE);
            buttonDeleteCarProfile.setVisibility(View.GONE);
            buttonReserve.setVisibility(View.VISIBLE);
        }

    }

    private void obserViewModel() {
        viewModel.getCarObservableData().observe(this, new Observer<Car>() {
            @Override
            public void onChanged(@Nullable Car car) {
                if (car != null) {
                    setVariables(car);
                }
            }
        });
    }

    private void setVariables(Car car) {
        this.car = car;
        textviewCarprofileName.setText(car.getName());
        textviewCarprofileFactory.setText(car.getFactory());
        textviewCarprofileYear.setText(String.valueOf(car.getYear()));
        textviewCarprofileKilometer.setText(String.valueOf(car.getKilometer()));
        textviewCarprofileColor.setText(car.getColor());
        textviewCarprofileCost.setText(String.valueOf(car.getPrice()));
        if (car.isAutomate()) {
            textviewCarprofileDescription.setText("AUTOMATE");
        } else {
            textviewCarprofileDescription.setText("MANUAL");
        }
        if (this.car.getUser_id() == UserHolder.Instance().getUser().getId()) {
            buttonReserve.setVisibility(View.GONE);
        }
        //setting image
        Log.i(CarProfileActivity.class.getSimpleName(), ApiRepository.getBaseUrl() + ApiService.imageApi + car.getImageUrl());
        Picasso.get()
                .load(ApiRepository.getBaseUrl() + ApiService.imageApi + car.getImageUrl())
                .into(imageviewCarLogo);
    }


    @OnClick({R.id.button_edit_carprofile, R.id.button_delete_car_profile, R.id.button_reserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_edit_carprofile:
                Intent intent = new Intent(CarProfileActivity.this, UpdateCarAdminActivity.class);
                intent.putExtra("Car", car);
                startActivity(intent);
                finish();
                break;
            case R.id.button_delete_car_profile:
                LiveData<Boolean> liveData = ApiRepository.getInstance().deleteCar(carId);
                obserDeleteRequest(liveData);
                break;
            case R.id.button_reserve:
                Intent i = new Intent(CarProfileActivity.this, RentCarActivity.class);
                i.putExtra("Car", car);
                startActivity(i);
                finish();
                break;
        }
    }

    private void obserDeleteRequest(LiveData<Boolean> liveData) {
        liveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean result) {
                if (result) {
                    Toast.makeText(CarProfileActivity.this, "Car Deleted Succussfullt", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CarProfileActivity.this, "Something went wrong ... please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getCar(carId);
    }
}
