package com.sooch.framework.ui.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sooch.framework.R;
import com.sooch.framework.data.entity.Repo;
import com.sooch.framework.data.repository.UserRepoImpl;
import com.sooch.framework.databinding.FragmentCameraBinding;
import com.sooch.framework.domain.interactor.GetReposUseCase;

import rx.Subscriber;

/**
 * Created by Takashi Sou on 2016/09/12.
 */
public class FragmentCamera extends Fragment {

    private static final String TAG = FragmentCamera.class.getSimpleName();

    private FragmentCameraBinding binding;

    // dependency injection UserRepoImpl
    private GetReposUseCase mGetReposUseCase = new GetReposUseCase("octocat", new UserRepoImpl());

    public FragmentCamera() {
    }

    public static FragmentCamera newInstance() {
        return new FragmentCamera();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);
        initView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        mGetReposUseCase.unsubscribe();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initView() {
        binding.btnGetRepo.setOnClickListener(view -> getRepos());
    }


    private void getRepos() {
        binding.txtDescription.setText(null);
        mGetReposUseCase.execute(new Subscriber<Repo>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getActivity(), "onCompleted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "onError!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Repo o) {
                String str = binding.txtDescription.getText().toString();
                str += "name:"+o.name+"\n";
                str += "url:"+o.url+"\n";
                binding.txtDescription.setText(str);
            }
        });
    }
}