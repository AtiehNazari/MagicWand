package view;

import android.app.Fragment;
import com.example.magicwand.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentOne extends Fragment {

	ImageView ivIcon;
	TextView tvItemName;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	public FragmentOne() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_history, container,
				false);

		ivIcon = (ImageView) view.findViewById(R.id.frag1_icon);
		tvItemName = (TextView) view.findViewById(R.id.frag1_text);

		return view;
	}

}
