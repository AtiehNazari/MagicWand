package view;

import java.util.ArrayList;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.magicwand.*;
import controller.BuyList;
import controller.DBController;
import controller.ListItem;
import controller.Product;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;
	private DBController dbc;
	private BuyList buyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing
		dbc = ((SmartListApplication) getApplication()).db;
		buyList = ((SmartListApplication) getApplication()).buyList;
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = "a";
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		// dataList.add(new DrawerItem(true)); // adding a spinner to the list

		dataList.add(new DrawerItem("Shop")); // adding a header to the list
		dataList.add(new DrawerItem("Current List", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("History", R.drawable.ic_action_good));

		dataList.add(new DrawerItem("Profile Settings"));// adding a header to
															// the list
		dataList.add(new DrawerItem("Add a Shopping Member",
				R.drawable.ic_action_search));
		dataList.add(new DrawerItem("Delete a Member",
				R.drawable.ic_action_cloud));
		dataList.add(new DrawerItem("Shop Selection",
				R.drawable.ic_action_camera));

		dataList.add(new DrawerItem("My Profile")); // adding a header to the
													// list
		dataList.add(new DrawerItem("Assigned Shopping",
				R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Name", R.drawable.ic_action_settings));
		dataList.add(new DrawerItem("Phone Number", R.drawable.ic_action_help));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// getActionBar().setHomeButtonEnabled(true);
		// getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setDisplayShowHomeEnabled(true);

		// LayoutInflater inflater = (LayoutInflater)
		// getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		// RelativeLayout layout = (RelativeLayout)
		// inflater.inflate(R.layout.actionbar_top, null);
		//
		//
		//
		// getActionBar().setCustomView(layout);
		// getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
		// ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// if (savedInstanceState == null) {
		//
		// if (dataList.get(0).isSpinner()
		// & dataList.get(1).getTitle() != null) {
		// SelectItem(2);
		// } else if (dataList.get(0).getTitle() != null) {
		// SelectItem(1);
		// } else {
		// SelectItem(0);
		// }
		// }

	}

	public void launchScanner(View v) {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			startActivityForResult(intent, 0);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	public void showList() {
		LinearLayout scroll = (LinearLayout) findViewById(R.id.id_scroll_linear);
		//scroll.removeAllViews();
		for (int i = 0; i < buyList.size(); i++) {
			LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout temp = (LinearLayout) layoutInflater.inflate(
					R.layout.shopping_items, null, true);
			TextView name = (TextView) temp.findViewById(R.id.id_item_name);
			TextView assignedTo = (TextView) temp
					.findViewById(R.id.id_item_assignment);
			TextView count = (TextView) temp.findViewById(R.id.id_item_count);
			assignedTo.setText(" " + buyList.getItem(i).getBuyer());
			name.setText(" " + buyList.getItem(i).getProduct().getName());
			count.setText(" " + buyList.getItem(i).getNum());
			scroll.addView(temp);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			final String bar = data.getStringExtra(ZBarConstants.SCAN_RESULT);
			final Dialog d = new Dialog(MainActivity.this);
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.add_item_to_list);

			final TextView itemName = (TextView) d
					.findViewById(R.id.id_wanted_item_name);
			final EditText itemCount = (EditText) d.findViewById(R.id.id_count);
			ImageView yes = (ImageView) d.findViewById(R.id.id_admit_button);
			ImageView no = (ImageView) d.findViewById(R.id.id_cancel_button);
			itemName.setText(bar);
			yes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String b = "";
					//b.replaceAll("\\s","");
					for(int i = 0 ; i < bar.length(); i++)
						if(bar.charAt(i)!=' ')
							b+=bar.charAt(i);
					
					System.err.println("BAR : " + b);
					Product p = dbc.getProductByBarcode(b);
					ListItem li = new ListItem(p, Integer.parseInt(itemCount
							.getText().toString()));
					buyList.addItem(li);
					showList();
					d.hide();
				}

			});
			no.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.hide();
				}
			});
			d.show();
		} else if (resultCode == RESULT_CANCELED && data != null) {
			String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
			if (!TextUtils.isEmpty(error)) {
				Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// RelativeLayout relativeLayout = (RelativeLayout) menu.findItem(
		// R.id.layout_item).getActionView();
		//
		// View inflatedView = getLayoutInflater().inflate(
		// R.layout.media_bottombar, null);
		//
		// relativeLayout.addView(inflatedView);

		return true;
	}

	public void SelectItem(int possition) {

		if (dataList.get(possition) != null) {
			Fragment fragment = null;
			fragment = new FragmentOne();
			Bundle args = new Bundle();
			mTitle = dataList.get(possition).getItemName();
			// System.err.println(possition);
			switch (possition) {
			case 1:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;

			case 2:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;
			// case 3:
			// // fragment = new FragmentOne();
			// args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
			// .getItemName());
			// args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
			// dataList.get(possition).getImgResID());
			// break;
			case 4:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;
			case 5:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;
			case 6:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;
			// case 7:
			// // fragment = new FragmentOne();
			// args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
			// .getItemName());
			// args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
			// dataList.get(possition).getImgResID());
			// break;
			case 8:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;
			case 9:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;
			case 10:
				// fragment = new FragmentOne();
				args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
						.getItemName());
				args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
						dataList.get(possition).getImgResID());
				break;

			default:
				break;
			}

			fragment.setArguments(args);
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction()
					.replace(R.id.id_shop_list_row, fragment).commit();

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
		}
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			System.err.println(dataList.size());
			// for(int i = 0 ; i < dataList.size(); i++){
			// if(dataList.get(i) != null){
			// System.err.println(i+ " "+ dataList.get(i).getImgResID() +
			// dataList.get(i).ItemName);
			// }
			// }
			if (dataList.get(position) != null) {
				if (dataList.get(position).getTitle() == null) {
					SelectItem(position);
				}
			}

		}
	}

}
