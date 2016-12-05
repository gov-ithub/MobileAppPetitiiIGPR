package ro.politiaromana.petitie.mobile.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ro.politiaromana.petitie.mobile.android.databinding.ActivityFaqBinding;
import ro.politiaromana.petitie.mobile.android.utils.ActivityUtils;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFaqBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_faq);

        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);
    }
}
