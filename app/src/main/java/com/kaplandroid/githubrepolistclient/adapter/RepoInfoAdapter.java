package com.kaplandroid.githubrepolistclient.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kaplandroid.githubrepolistclient.R;
import com.kaplandroid.githubrepolistclient.restapi.model.response.RepoInfo;
import com.kaplandroid.githubrepolistclient.util.DateUtil;
import com.kaplandroid.githubrepolistclient.util.DialogUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KAPLANDROID on 2.09.2016.
 */
public class RepoInfoAdapter extends RecyclerView.Adapter<RepoInfoAdapter.RepoInfoViewHolder> {

    private List<RepoInfo> RepoInfoList;

    class RepoInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cvRepoList_tvRepoName)
        TextView tvRepoName;
        @BindView(R.id.cvRepoList_tvRepoUpdateInfo)
        TextView tvRepoUpdateInfo;
        @BindView(R.id.cvRepoList_cvRepoRoot)
        CardView cvRepoRoot;

        RepoInfoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public RepoInfoAdapter(List<RepoInfo> RepoInfosList) {
        this.RepoInfoList = RepoInfosList;
    }

    @Override
    public RepoInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_repo_list_item, parent, false);

        return new RepoInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RepoInfoViewHolder holder, int position) {
        final RepoInfo repoInfo = RepoInfoList.get(position);
        holder.tvRepoName.setText(repoInfo.getName());
        holder.tvRepoUpdateInfo.setText(
                DateUtil.getUpdateTimeText(holder.tvRepoName.getContext(),
                        repoInfo.getUpdated_at(),
                        Calendar.getInstance().getTime()));

        holder.cvRepoRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showDialogMessage(holder.cvRepoRoot.getContext(), repoInfo.getShortInfo(holder.cvRepoRoot.getContext()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return RepoInfoList.size();
    }
}