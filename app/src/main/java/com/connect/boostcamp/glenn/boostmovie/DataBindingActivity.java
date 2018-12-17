package com.connect.boostcamp.glenn.boostmovie;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.connect.boostcamp.glenn.boostmovie.api.NaverMovieAPI;
import com.connect.boostcamp.glenn.boostmovie.databinding.DatabindingActivityBinding;
import com.connect.boostcamp.glenn.boostmovie.model.Movie;
import com.connect.boostcamp.glenn.boostmovie.model.MovieListInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataBindingActivity extends AppCompatActivity {

    private final int LOADITEMCOUNT = 10;
    private DatabindingActivityBinding binding;
    private DataBindingAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private String inputWord = "";
    private String searchedWord = "";

    public void setInputWord(String input) { this.inputWord = input; }

    public String getInputWord() { return this.inputWord; }

    public void setSearchedWord(String input) { this.searchedWord = input; }

    public String getSearchedWord() { return this.searchedWord; }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.databinding_activity);
        binding.setActivity(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rcContent.setLayoutManager(linearLayoutManager);
        adapter = new DataBindingAdapter(this);
        binding.rcContent.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int start) {
                selectWord(start + LOADITEMCOUNT);
            }
        };
        binding.rcContent.addOnScrollListener(scrollListener);
    }

    public void onButtonClick(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.tvEdit.getWindowToken(), 0);

        String searchWord = binding.tvEdit.getText().toString();
        binding.tvEdit.getText().clear();

        if(searchWord.trim().length() == 0) {
            Toast.makeText(this,"검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            setInputWord(searchWord);
            scrollListener.setLoading(true);
            selectWord(1);
        }
    }

    private void selectWord(int start) {
        String searchWord = "";
        if(start == 1) {
            searchWord = getInputWord();
        } else if (start > 1) {
            searchWord = getSearchedWord();
        }
        setRecyclerView(start, searchWord);
    }

    private void setRecyclerView(final int start, final String searchWord) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openapi.naver.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NaverMovieAPI service = retrofit.create(NaverMovieAPI.class);

        Map<String, String> map = new HashMap<>();
        map.put("query",searchWord);
        map.put("display",Integer.toString(LOADITEMCOUNT));
        map.put("start",Integer.toString(start));

        Call<MovieListInfo> repos = service.listRepos(map);

        final ProgressDialog mDlg = new ProgressDialog(DataBindingActivity.this);
        if(start == 1) {
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage("잠시만요...");
            mDlg.show();
        }

        repos.enqueue(new Callback<MovieListInfo>() {
            @Override
            public void onResponse(Call<MovieListInfo> call, Response<MovieListInfo> response) {
                MovieListInfo movieListInfo = response.body();
                if( !movieListInfo.getTotal().equals("0") ) {
                    List<Movie> movieList = movieListInfo.getItems();
                    if(start == 1) {
                        setSearchedWord(searchWord);
                        scrollListener.setJsonTotal(Integer.parseInt(movieListInfo.getTotal()));
                        scrollListener.setStart(start);
                        adapter.updateItems(movieList);
                        binding.rcContent.getLayoutManager().scrollToPosition(0);
                    } else if (start > 1) {
                        scrollListener.setStart(start);
                        adapter.addItems(movieList);
                    } else {
                        Toast.makeText(getApplicationContext(), "start < 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    StringBuilder result = new StringBuilder("\"");
                    result.append(searchWord);
                    result.append("\"");
                    result.append("의 검색결과는 없습니다..");
                    Toast.makeText(getApplicationContext(),result.toString(), Toast.LENGTH_SHORT).show();
                }
                scrollListener.setLoading(false);
                if(start == 1) {
                    mDlg.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MovieListInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "데이터 수신 오류 발생. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                if(start == 1) {
                    mDlg.dismiss();
                }
                call.cancel();
            }
        });
    }
}
