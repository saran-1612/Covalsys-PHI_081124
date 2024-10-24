package com.covalsys.phi_scanner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.covalsys.phi_scanner.BR;
import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.databinding.FragmentHomeBinding;
import com.covalsys.phi_scanner.ui.base.BaseFragment;
import com.covalsys.phi_scanner.ui.screen_shot.ListImageFragment;

public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> implements HomeNavigator {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Override
    public int getBindingVariable() {
        return BR.homeViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel.setNavigator(this);
        setUp();
        return dataBinding.getRoot();
    }

    private void setUp() {

        dataBinding.stockScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data", "data");
                Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.action_nav_home_to_nav_inv_history, bundle);
            }
        });

        dataBinding.informationStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data", "data");
                Navigation.findNavController(requireActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.action_nav_home_to_nav_store_information, bundle);
                /*informationFragment();*/
            }
        });

        /*dataBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPrimaryAdapter.getFilter().filter(newText);
                return true;
            }
        });*/

       /* dataBinding.itemViewPager.setAdapter(new MyViewPagers(getActivity()));
        dataBinding.itemViewPager.startAutoScroll();
        dataBinding.itemViewPager.setCycle(true);*/
        //dataBinding.indicator.setViewPager(dataBinding.itemViewPager);

        /*LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dataBinding.primaryRecyclerView.setLayoutManager(primaryManager);
        dataBinding.primaryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.primaryRecyclerView.setAdapter(mPrimaryAdapter);*/

      /*  dataBinding.tvPrimaryMore.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", true);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_customers, bundle);
        });

        dataBinding.tvSecondaryMore.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", false);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_customers, bundle);
        });*/

    }

    private void informationFragment() {
        ListImageFragment imageFragment = new ListImageFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame1,imageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //Set title
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home");
        }
    }
}
