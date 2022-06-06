package com.example.getfixapplication.ui.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityCameraBinding
import com.example.getfixapplication.ml.Model
import com.example.getfixapplication.utils.ConstVal.CAMERA_X_RESULT
import com.example.getfixapplication.utils.ConstVal.PERMISSIONS
import com.example.getfixapplication.utils.createFile
import com.example.getfixapplication.utils.showToast
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var cameraExecutor: ExecutorService
    lateinit var bitmap: Bitmap

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                showToast(this, "Error")
            }
        }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun getPermissions() {
        if (hasPermissions(this, PERMISSIONS)) {
            showToast(this, "Has Permissions")
        } else {
            permReqLauncher.launch(PERMISSIONS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()

        getPermissions()
        startCamera()

        binding.tvCameraBatal.setOnClickListener {
            finish()
        }

        binding.ivCameraCapture.setOnClickListener { takePhoto() }

        val labels = application.assets.open("label.txt").bufferedReader().use { it.readText() }.split("\n")

//        select_image_button.setOnClickListener(View.OnClickListener {
//            Log.d("mssg", "button pressed")
//            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = "image/*"
//
//            startActivityForResult(intent, 250)
//        })


//            var resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val model = Model.newInstance(this)

//            var tbuffer = TensorImage.fromBitmap(resized)
//            var byteBuffer = tbuffer.buffer
//
//// Creates inputs for reference.
//            val inputFeature0 =
//                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//            inputFeature0.loadBuffer(byteBuffer)

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)

            val input = Bitmap.createScaledBitmap(bitmap ,150, 150, true)
            val image = TensorImage(DataType.FLOAT32)
            image.load(input)
            val byteBuffer = image.buffer
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max = getMax(outputFeature0.floatArray)

            binding.tvCameraUpload.setText(labels[max])

// Releases model resources if no longer used.
            model.close()
    }

    public override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    showToast(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                    )
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra("picture", photoFile)
                    intent.putExtra(
                        "isBackCamera",
                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    setResult(CAMERA_X_RESULT, intent)
                    finish()
                }
            }
        )
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.vfCameraContainer.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                showToast(this@CameraActivity,"Gagal memunculkan kamera.")
            }
        }, ContextCompat.getMainExecutor(this))
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if(requestCode == 250){
                binding.ivCameraCapture.setImageURI(data?.data)

                var uri : Uri?= data?.data
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            }
            else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
                bitmap = data?.extras?.get("data") as Bitmap
                binding.ivCameraCapture.setImageBitmap(bitmap)
            }

        }

        fun getMax(arr:FloatArray) : Int{
            var ind = 0;
            var min = 0.0f;

            for(i in 0..1)
            {
                if(arr[i] > min)
                {
                    min = arr[i]
                    ind = i;
                }
            }
            return ind
        }
}
