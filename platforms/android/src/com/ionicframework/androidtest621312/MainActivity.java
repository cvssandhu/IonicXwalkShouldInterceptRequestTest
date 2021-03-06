/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.ionicframework.androidtest621312;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import org.apache.cordova.*;


// X WALK TEST STARTS HERE
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import android.webkit.WebResourceResponse;
// X WALK TEST ENDS HERE

public class MainActivity extends CordovaActivity
{

    private Boolean loadInternal = false;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.init();
        // Set by <content src="index.html" /> in config.xml


        // X WALK TEST STARTS HERE
        XWalkView webView =  ((XWalkView) this.appView.getEngine().getView()); //.getSettings();
        initializeXWalkViewClients(webView);
        // X WALK TEST ENDS HERE

        loadUrl(launchUrl);

    }

    private void initializeXWalkViewClients(XWalkView xwalkView) {
        xwalkView.setResourceClient(new XWalkResourceClient(xwalkView) {
            
            @Override
            public WebResourceResponse shouldInterceptLoadRequest(XWalkView view, String url) {

                if (url != null && url.startsWith("loadinternal") ) {
                    loadInternal = true;
                    return new WebResourceResponse("text/plain", "UTF-8", null);
                }
                if (url != null && url.startsWith("something") ) {
                    loadInternal = false;
                    return new WebResourceResponse("text/plain", "UTF-8", null);
                }

                if (url != null && (url.startsWith("http://") || url.startsWith("https://")) && !loadInternal ) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                    return new WebResourceResponse("text/plain", "UTF-8", null);
                } else {
                    return null;
                }

            }
        });

    }

}
