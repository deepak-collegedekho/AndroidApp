package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.activity.VideoPlayerActivity;
import com.collegedekho.app.entities.VideoEntry;
import com.collegedekho.app.resource.Constants;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bashir on 11/2/16.
 */
public class InstituteVideosFragment extends BaseFragment {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    RecyclerView videosRecycler;
    ArrayList<String> videoIdList;
    ArrayList<VideoEntry> videoList;
    VideoListAdapter videoListAdapter;
    String url;
    private OnTitleUpdateListener titleListener;
    private Calendar calendar;

    public static InstituteVideosFragment newInstance(ArrayList<String> videoList) {

        Bundle args = new Bundle();
        args.putStringArrayList("institute_videos_list", videoList);
        InstituteVideosFragment fragment = new InstituteVideosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.videoIdList = bundle.getStringArrayList("institute_videos_list");

            this.videoList = new ArrayList<VideoEntry>();
            calendar=Calendar.getInstance();
            StringBuilder builder = new StringBuilder();
            builder.append("https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails,statistics&id=");
            int listSize = videoIdList.size();
            for (int i = 0; i < listSize; i++) {
                builder.append(videoIdList.get(i));
                if (i < listSize) {
                    builder.append(",");
                }
                VideoEntry entry = new VideoEntry();
                entry.setVideoId(this.videoIdList.get(i));
                this.videoList.add(entry);
            }
            builder.append("&key=" + Constants.YOUTUBE_DEVELOPER_KEY);
            url = builder.toString();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.institute_videos_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videosRecycler = (RecyclerView) view.findViewById(R.id.institute_videos_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videosRecycler.setLayoutManager(layoutManager);
        if (videoIdList != null && !videoIdList.isEmpty()) {
            titleListener.onUpdate(videoList, url, this);
        }
        videoListAdapter = new VideoListAdapter(getActivity(), videoList);
        videosRecycler.setAdapter(videoListAdapter);
        checkYouTubeApi();
    }


    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getActivity());
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }


    public void updateVideosList(List<VideoEntry> videos) {
        if (videos != null && !videos.isEmpty()) {
            this.videoList = new ArrayList<>(videos);
            videoListAdapter.notifyDataSetChanged();
        }
    }

    class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder> {

        ArrayList<VideoEntry> videoList;
        private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;

        private final List<View> entryViews;
        private final LayoutInflater inflater;
//        private final ThumbnailListener thumbnailListener;

        private boolean labelsVisible;

        private final int UNINITIALIZED = 1;
        private final int INITIALIZING = 2;
        private final int INITIALIZED = 3;

        public VideoListAdapter(Context context, ArrayList<VideoEntry> videoList) {
            this.videoList = videoList;

            entryViews = new ArrayList<View>();
            thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
            inflater = LayoutInflater.from(context);
//            thumbnailListener = new ThumbnailListener();

            labelsVisible = true;
        }

        @Override
        public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.video_list_item, parent, false);

            return new VideoListViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(VideoListViewHolder holder, int position) {
            final VideoEntry entry = videoList.get(position);
//
//            // There are three cases here
//            if (view == null) {
//                // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
//                holder.thumbnail.setTag(entry.videoId);
//                holder.thumbnail.initialize(Constants.YOUTUBE_DEVELOPER_KEY, thumbnailListener);
//            } else {
//                YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(holder.thumbnail);
//                if (loader == null) {
//                    // 2) The view is already created, and is currently being initialized. We store the
//                    //    current videoId in the tag.
//                    holder.thumbnail.setTag(entry.videoId);
//                } else {
//                    // 3) The view is already created and already initialized. Simply set the right videoId
//                    //    on the loader.
//                    holder.thumbnail.setImageResource(R.drawable.loading_thumbnail);
//                    loader.setVideo(entry.videoId);
//                }
//            }
      /*      holder.thumbnail.initialize(Constants.YOUTUBE_DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(entry.videoId);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailLoader.release();
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }
                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });*/
            int state = (int) holder.thumbnail.getTag(R.id.initialize);

            if(state == UNINITIALIZED){
                holder.initialize();
            }
            else if(state == INITIALIZED){
                YouTubeThumbnailLoader loader = (YouTubeThumbnailLoader) holder.thumbnail.getTag(R.id.thumbnailloader);
                loader.setVideo(entry.getVideoId());
            }
            holder.label.setText(entry.text);
            holder.duration.setText(entry.duration);
            holder.viewsCount.setText(entry.viewCount);
            holder.label.setVisibility(labelsVisible ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getItemCount() {
            return videoList.size();
        }

        public class VideoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView label;
            private TextView duration;
            private TextView viewsCount;
            private TextView published;
            private YouTubeThumbnailView thumbnail;

            public VideoListViewHolder(View itemView) {
                super(itemView);
                label = ((TextView) itemView.findViewById(R.id.text));
                duration = (TextView) itemView.findViewById(R.id.duration);
                viewsCount = (TextView) itemView.findViewById(R.id.views_count);
                published=(TextView)itemView.findViewById(R.id.published);
                thumbnail = (YouTubeThumbnailView) itemView.findViewById(R.id.thumbnail);
                itemView.setOnClickListener(this);
                initialize();
            }

            public void initialize(){
                thumbnail.setTag(R.id.initialize, INITIALIZING);
                thumbnail.setTag(R.id.thumbnailloader, null);
                thumbnail.setTag(R.id.videoid, "");

                thumbnail.initialize(Constants.YOUTUBE_DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                        thumbnail.setTag(R.id.initialize, INITIALIZED);
                        thumbnail.setTag(R.id.thumbnailloader, youTubeThumbnailLoader);

                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                            @Override
                            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String loadedVideoId) {
//                                String currentVideoId = (String) thumbnail.getTag(R.id.videoid);
//                                if(currentVideoId.equals(loadedVideoId)) {
//                                    ivYtLogo.setBackgroundColor(transparentColor);
//                                }
//                                else{
//                                    ivYtLogo.setBackgroundColor(blackColor);
//                                }
                            }

                            @Override
                            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                                youTubeThumbnailView.setImageResource(R.drawable.no_thumbnail);
                            }
                        });

                        String videoId = (String) thumbnail.getTag(R.id.videoid);
                        if(videoId != null && !videoId.isEmpty()){
                            youTubeThumbnailLoader.setVideo(videoId);
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        thumbnail.setTag(R.id.initialize, UNINITIALIZED);
                        youTubeThumbnailView.setImageResource(R.drawable.no_thumbnail);
                    }
                });
            }


        @Override
            public void onClick(View v) {
                String videoId = videoList.get(getLayoutPosition()).getVideoId();
                Intent intent = new Intent(v.getContext(), VideoPlayerActivity.class);
                intent.putExtra("video_id", videoId);
                startActivity(intent);
            }
        }

     /*   private final class ThumbnailListener implements
                YouTubeThumbnailView.OnInitializedListener,
                YouTubeThumbnailLoader.OnThumbnailLoadedListener {

            @Override
            public void onInitializationSuccess(
                    YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
                loader.setOnThumbnailLoadedListener(this);
                thumbnailViewToLoaderMap.put(view, loader);
                view.setImageResource(R.drawable.loading_thumbnail);
                String videoId = (String) view.getTag();
                loader.setVideo(videoId);
            }

            @Override
            public void onInitializationFailure(
                    YouTubeThumbnailView view, YouTubeInitializationResult loader) {
                view.setImageResource(R.drawable.no_thumbnail);
            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
                view.setImageResource(R.drawable.no_thumbnail);
            }
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity)
                titleListener = (OnTitleUpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTitleUpdateListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        titleListener = null;
    }

    public interface OnTitleUpdateListener {
        public void onUpdate(List<VideoEntry> videoList, String url, InstituteVideosFragment fragment);
    }

}
