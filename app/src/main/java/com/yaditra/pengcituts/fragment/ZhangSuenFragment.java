package com.yaditra.pengcituts.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yaditra.pengcituts.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yanfa on 10/26/2015.
 */
public class ZhangSuenFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQ_CODE_PICK_IMAGE = 2;

    private TextView textViewTotalColor;
    private ImageView cameraView;
    private ImageView equalizedView;
    private Button cameraButton;
    private Button imagePickerButton;
    private SeekBar pixelSeekBar;
    private SeekBar seekBar2;
    private Bitmap currentImageBitmap;
    private Boolean pictureTaken;

    //ini di komenin soalnya belom ada kelas ImageProcessornyaaa
    //private ImageProcessor imageProcessor;

    public ZhangSuenFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view, container, false);

        //ini di komenin soalnya belom ada kelas ImageProcessornyaaa
        //imageProcessor = new ImageProcessor();
        pictureTaken = false;
        cameraView = (ImageView)rootView.findViewById(R.id.cameraImage);
        equalizedView = (ImageView)rootView.findViewById(R.id.equalizedImage);
        textViewTotalColor = (TextView)rootView.findViewById(R.id.textViewTotalWarna);

        pixelSeekBar = (SeekBar)rootView.findViewById(R.id.pixelSeekBar);
        cameraButton = (Button)rootView.findViewById(R.id.cameraButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                //System.out.println("width : " + pixelSeekBar.getProgress());
            }
        });

        imagePickerButton = (Button)rootView.findViewById(R.id.imageChooserButton);
        imagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(v);
            }
        });

        pixelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(pictureTaken) {
                    System.out.println("masuk kok");
                    int progressChange = (int) (seekBar.getProgress() * 1000000);

                    //ini di komenin soalnya belom ada kelas ImageProcessornyaaa
                    //equalizedView.setImageBitmap(ImageProcessor.toGrayscale(ImageProcessor.histogramEqualization(currentImageBitmap, progressChange, 0)));


                    //---------------------------------------------------------------------------------//
                    //---------------------------------------------------------------------------------//
                    // ------------ ini buat ganti ganti yang di histogramnyaa yaaaa ------------------//
                    //---------------------------------------------------------------------------------//
                    //---------------------------------------------------------------------------------//
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return rootView;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap tempBitmap = (Bitmap) extras.get("data");

            //ini di komenin soalnya belom ada kelas ImageProcessornyaaa
            //currentImageBitmap = ImageProcessor.toGrayscale(tempBitmap);
            cameraView.setImageBitmap(tempBitmap);

            //ini di komenin soalnya belom ada kelas ImageProcessornyaaa
            //equalizedView.setImageBitmap(ImageProcessor.toGrayscale(ImageProcessor.histogramEqualization(currentImageBitmap, 0, 0)));
            //textViewTotalColor.setText("Jumlah Warna : " + ImageProcessor.countTotalColor(currentImageBitmap));

            pictureTaken = true;
        }
        else if(requestCode == REQ_CODE_PICK_IMAGE && resultCode == getActivity().RESULT_OK){
            InputStream stream = null;
            try {
                // recyle unused bitmaps
                if (currentImageBitmap != null) {
                    currentImageBitmap.recycle();
                }
                stream = getActivity().getContentResolver().openInputStream(data.getData());
                currentImageBitmap = BitmapFactory.decodeStream(stream);

                cameraView.setImageBitmap(currentImageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            //---------------------------------------------------------------------------------//
            //---------------------------------------------------------------------------------//
            //------------------- nah ganti-ganti algoritmanya disini yaaa --------------------//
            //------------- note : kalo emang ada prosedut yang ga bisa di panggil ------------//
            //--------------- langsung coba make getActivity().prosedurenya -------------------//
            //---------------------------------------------------------------------------------//
            //---------------------------------------------------------------------------------//

            //ini dikomenin sebagai contoh aja
            /*cameraView.setImageBitmap(ImageProcessor.toGrayscale(currentImageBitmap));
            currentImageBitmap = imageProcessor.toGrayscale(imageProcessor.fastblur(imageProcessor.toGrayscale(currentImageBitmap), (float) 0.5, 1), 0);
            equalizedView.setImageBitmap(currentImageBitmap);

            // False artinya untuk plat, True artinya untuk model
            textViewTotalColor.setText("Point: " + imageProcessor.getExtremePoints(currentImageBitmap, 0, 0, false));
            imageProcessor.gridFullObject(currentImageBitmap, false);*/
        }
    }

}

