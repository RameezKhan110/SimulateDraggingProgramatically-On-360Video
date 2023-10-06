package com.example.a360video


import android.annotation.SuppressLint
import android.media.metrics.PlaybackStateEvent.STATE_ENDED
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.example.a360video.databinding.ActivityMainBinding


@UnstableApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var exoPlayer: androidx.media3.exoplayer.ExoPlayer? = null
    private lateinit var dataSource: RawResourceDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataSource = RawResourceDataSource(this)
        dataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.myvideo)))
        dataSource.uri?.let {
            initPlayer(it)
        }

        binding.UP.setOnClickListener {
            simulateDragUp(binding.playerView)
        }
        binding.DOWN.setOnClickListener {
            simulateDragDown(binding.playerView)
        }
        binding.RIGHT.setOnClickListener {
            simulateDragRight(binding.playerView)
        }
        binding.LEFT.setOnClickListener {
            simulateDragLeft(binding.playerView)
        }
    }

    private fun initPlayer(video: Uri) {

        exoPlayer = androidx.media3.exoplayer.ExoPlayer.Builder(this)
            .build()
            .apply {

                setMediaSource(getProgressiveMediaSource(video))
                prepare()
                addListener(playerListener)
            }
    }

    private fun getProgressiveMediaSource(video: Uri): androidx.media3.exoplayer.source.MediaSource {


        return ProgressiveMediaSource.Factory { dataSource }
            .createMediaSource(MediaItem.fromUri(video))
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.playWhenReady = false
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }


    private fun releasePlayer() {
        exoPlayer?.apply {
            playWhenReady = false
            release()
        }
        exoPlayer = null
    }

    private fun restartPlayer() {
        exoPlayer?.seekTo(0)
        exoPlayer?.playWhenReady = true
    }

    private val playerListener = object : androidx.media3.common.Player.Listener {
        @SuppressLint("SwitchIntDef")
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                STATE_ENDED -> restartPlayer()
                STATE_READY -> {

                    binding.playerView.player = exoPlayer
                    exoPlayer?.playWhenReady = true
                }
            }
        }
    }

    private fun simulateDragRight(view: View) {
        val downTime = System.currentTimeMillis()
        val eventTime = System.currentTimeMillis()
        val deltaX = -200f

        val downEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_DOWN,
            0f,
            0f,
            0
        )

        val moveEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_MOVE,
            deltaX,
            0f,
            0
        )

        val upEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_UP,
            deltaX,
            0f,
            0
        )

        view.dispatchTouchEvent(downEvent)
        view.dispatchTouchEvent(moveEvent)
        view.dispatchTouchEvent(upEvent)

        downEvent.recycle()
        moveEvent.recycle()
        upEvent.recycle()
    }

    private fun simulateDragLeft(view: View) {
        val downTime = System.currentTimeMillis()
        val eventTime = System.currentTimeMillis()
        val deltaX = 200f

        val downEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_DOWN,
            0f,
            0f,
            0
        )

        val moveEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_MOVE,
            deltaX,
            0f,
            0
        )

        val upEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_UP,
            deltaX,
            0f,
            0
        )

        view.dispatchTouchEvent(downEvent)
        view.dispatchTouchEvent(moveEvent)
        view.dispatchTouchEvent(upEvent)

        downEvent.recycle()
        moveEvent.recycle()
        upEvent.recycle()
    }

    private fun simulateDragUp(view: View) {
        val downTime = System.currentTimeMillis()
        val eventTime = System.currentTimeMillis()
        val deltaY = 200f

        val downEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_DOWN,
            0f,
            0f,
            0
        )

        val moveEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_MOVE,
            0f,
            deltaY,
            0
        )

        val upEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_UP,
            0f,
            deltaY,
            0
        )

        view.dispatchTouchEvent(downEvent)
        view.dispatchTouchEvent(moveEvent)
        view.dispatchTouchEvent(upEvent)

        downEvent.recycle()
        moveEvent.recycle()
        upEvent.recycle()
    }

    private fun simulateDragDown(view: View) {
        val downTime = System.currentTimeMillis()
        val eventTime = System.currentTimeMillis()
        val deltaY = -200f

        val downEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_DOWN,
            0f,
            0f,
            0
        )

        val moveEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_MOVE,
            0f,
            deltaY,
            0
        )

        val upEvent = MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_UP,
            0f,
            deltaY,
            0
        )

        view.dispatchTouchEvent(downEvent)
        view.dispatchTouchEvent(moveEvent)
        view.dispatchTouchEvent(upEvent)

        downEvent.recycle()
        moveEvent.recycle()
        upEvent.recycle()
    }

}