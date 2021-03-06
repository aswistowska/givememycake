package org.swistowski.agata.givememycake.fragments;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.swistowski.agata.givememycake.R;
import org.swistowski.agata.givememycake.activities.StepDetailActivity;
import org.swistowski.agata.givememycake.activities.StepListActivity;
import org.swistowski.agata.givememycake.model.Recipe;
import org.swistowski.agata.givememycake.model.Step;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {


    public static final String ARG_RECIPE = "recipe";
    public static final String ARG_STEP_ID = "stepId";
    private static final String PLAYBACK_POSITION = "playbackPosition";

    private Recipe mRecipe;
    private Step mStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private long mCurrentPosition = -1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_RECIPE) && getArguments().containsKey(ARG_STEP_ID)) {

            mRecipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
            int stepId = getArguments().getInt(ARG_STEP_ID);
            mStep = mRecipe.getStepById(stepId);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mRecipe.getName());
            }

        }
    }

    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "GiveMeMyCake");

            if(mStep.getVideo() != null) {
                Uri video = Uri.parse(mStep.getVideo().toString());
                MediaSource mediaSource = new ExtractorMediaSource(video, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
            }
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        if (mStep != null) {
            ((TextView) rootView.findViewById(R.id.step_detail)).setText(mStep.getDescription());
        }
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
        }

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPlayerView = (SimpleExoPlayerView) getView().findViewById(R.id.playerView);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.video_placeholder));
        initializePlayer();
        if (mCurrentPosition != -1) {
            mExoPlayer.seekTo(mCurrentPosition);
            mExoPlayer.setPlayWhenReady(true);
        } else {
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurrentPosition = mExoPlayer.getCurrentPosition();
        releasePlayer();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(PLAYBACK_POSITION, mCurrentPosition);
    }
}
