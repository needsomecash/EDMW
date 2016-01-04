package xyz.edmw.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ortiz.touch.TouchImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.edmw.R;

public class ImageDialogFragment extends DialogFragment {
    @Bind(R.id.image_view)
    TouchImageView imageView;

    public static final String tag = "ImageDialogFragment";
    private static final String ARG_BITMAP = "arg_bitmap";

    public static ImageDialogFragment newInstance(Bitmap bitmap) {
        ImageDialogFragment fragment = new ImageDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BITMAP, bitmap);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageDialogFragment() {

    }

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_image, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bitmap bitmap = getArguments().getParcelable(ARG_BITMAP);
        imageView.setImageBitmap(bitmap);
    }
}