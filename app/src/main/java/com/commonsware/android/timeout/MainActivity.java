/***
  Copyright (c) 2013 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  Covered in detail in the book _The Busy Coder's Guide to Android Development_
    https://commonsware.com/Android
 */

package com.commonsware.android.timeout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

public class MainActivity extends Activity implements Runnable {
  private View content=null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    content=findViewById(android.R.id.content);

      boolean hasPermissions = Settings.System.canWrite(getApplicationContext());
      if(hasPermissions) {
        Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT, 300);
        new android.os.Handler().postDelayed(
                new Runnable() {
                  public void run() {
                    try {
                      //int defaultTurnOffTime =  Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT, 16000);
                      //Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT, defaultTurnOffTime);
                      Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_OFF_TIMEOUT, 300000);
                      System.out.println("Going to sleep...");
                      System.out.println(" ############### Settings.System.SCREEN_OFF_TIMEOUT ############### : " + Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT));
                      finish();
                    } catch (Settings.SettingNotFoundException e) {
                      e.printStackTrace();
                    }
                  }
                },
                10000);
      } else{
        System.out.println("!!!!!! No permissions - Going to ask Permissions !!!!!!   ");
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        startActivity(intent);
      }
  }

  @Override
  public void onDestroy() {
    content.removeCallbacks(this);
    super.onDestroy();
  }

  @Override
  public void run() {

  }

}
