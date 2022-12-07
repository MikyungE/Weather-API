package com.example.finedust

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finedust.RecyclerVierAdapter.OnItemClickEventListener
import com.example.finedust.data.Repository
import com.example.finedust.data.model.airquality.Grade
import com.example.finedust.data.model.airquality.MeasuredValue
import com.example.finedust.data.model.monitoringstation.MonitoringStation
import com.example.finedust.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private var cancellationTokenSource: CancellationTokenSource? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var pmMax: String;
    private lateinit var pmMin: String;
    private val scope = MainScope()
    var adapter = RecyclerVierAdapter()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        getData()
        bindViews()
        initVariables()
        requestLocationPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
        scope.cancel()
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationPermissionGranted =
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!locationPermissionGranted) {
                finish()
            } else {
                val backgroundLocationPermissionGranted =
                    ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                val shouldShowBackgroundPermissionRationale =
                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

                if (!backgroundLocationPermissionGranted && shouldShowBackgroundPermissionRationale) {
                    showBackgroundLocationPermissionRationaleDialog()
                } else {
                    fetchAirQualityData()
                }
            }
        } else {
            if (!locationPermissionGranted) {
                finish()
            } else {
                fetchAirQualityData()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun showBackgroundLocationPermissionRationaleDialog() {
        AlertDialog.Builder(this)
            .setMessage("홈 위젯을 사용하려면 위치 접근 권한이 ${packageManager.backgroundPermissionOptionLabel} 상태여야 합니다.")
            .setPositiveButton("설정하기") { dialog, _ ->
                requestBackgroundLocationPermissions()
                dialog.dismiss()
            }
            .setNegativeButton("그냥두기") { dialog, _ ->
                fetchAirQualityData()
                dialog.dismiss()
            }
            .show()
    }

    private fun initVariables() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestBackgroundLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS
        )
    }

    @SuppressLint("MissingPermission")
    private fun fetchAirQualityData() {
        cancellationTokenSource = CancellationTokenSource()

        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource!!.token
        ).addOnSuccessListener { location ->
            scope.launch {
                binding.errorDescriptionTextView.visibility = View.GONE
                try {
                    val monitoringStation =
                        Repository.getNearbyMonitoringStation(37.648909, 127.064121);
                                                                    // 한국성대학교의 위치 임의 설정.
                    val measuredValue =
                        Repository.getLatestAirQualityData(monitoringStation!!.stationName!!)

                    displayAirQualityData(monitoringStation, measuredValue!!)
                } catch (exception: Exception) {
                    binding.errorDescriptionTextView.visibility = View.VISIBLE
                    binding.contentsLayout.alpha = 0F
                } finally {
                    binding.progressBar.visibility = View.GONE
                    binding.refresh.isRefreshing = false
                }
            }
        }
    }

    private fun bindViews() {
        binding.refresh.setOnRefreshListener {
            fetchAirQualityData()
        }
    }

    @SuppressLint("SetTextI18n")
    fun displayAirQualityData(
        monitoringStation: MonitoringStation,
        measuredValue: MeasuredValue
    ) {
        binding.contentsLayout.animate()
            .alpha(1F)
            .start()
        binding.measuringStationNameTextView.text = monitoringStation.stationName       // 노원구

        (measuredValue.khaiGrade ?: Grade.UNKNOWN).let { grade ->
            binding.root.setBackgroundResource(grade.colorResId)
            binding.totalGraddeLabelTextView.text = grade.label
            binding.totalGradeEmojiTextView.text = grade.emoji      // 좋음, 나쁨 처리
        }

        with(measuredValue) {
            binding.fineDustInformationTextView.text =
                "미세먼지: $pm10Value ㎍/㎥ ${(pm10Grade ?: Grade.UNKNOWN).emoji}"    // 받아온 변수 출력.
            binding.ultraFineDuistInformationTextView.text =
                "초미세먼지: $pm25Value ㎍/㎥ ${(pm25Grade ?: Grade.UNKNOWN).emoji}"
        }
    }

    companion object {      // 권한.
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 100
        private const val REQUEST_BACKGROUND_ACCESS_LOCATION_PERMISSIONS = 101
    }

    private fun init() {                            // 초기화. 기능 추가를 위함.
        val recyclerView = binding.itemRecyclers
        val gridLayoutManager = GridLayoutManager(this,2)
        recyclerView.setLayoutManager(gridLayoutManager)
        recyclerView.setAdapter(adapter)
        adapter.setOnClickListener(object : OnItemClickEventListener {
            override fun onItemClick(v: View) {
            }
        })
    }

    private fun getData() {         // 액티비티가 넘어왔을 때, 동작. (다음 페이지에서)
        adapter.addItem("장치 추가하기",R.drawable.ic_launcher_foreground)
        intent.getStringExtra("title")?.let { adapter.addItem(it,R.drawable.awful) };
    }
}