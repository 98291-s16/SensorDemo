package com.orobator.sensordemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by AndrewOrobator on 3/30/16.
 */
public class NfcFragment extends Fragment {
    private TextView tagIdTextView;
    private TextView techListTextView;
    private TextView payloadTextView;
    private PendingIntent pendingIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);
        tagIdTextView = (TextView) view.findViewById(R.id.tag_id_TextView);
        techListTextView = (TextView) view.findViewById(R.id.nfc_tech_TextView);
        payloadTextView = (TextView) view.findViewById(R.id.payload_TextView);

        Intent intent = new Intent(getActivity(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        nfcAdapter.enableForegroundDispatch(getActivity(), pendingIntent, null, null);
    }

    @Override
    public void onPause() {
        super.onPause();

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        nfcAdapter.disableForegroundDispatch(getActivity());
    }

    public void updateTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        techListTextView.setText("Tech list: " + Arrays.toString(tag.getTechList()));

        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMsgs != null) {
            NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
            }

            payloadTextView.setText("Payload: " + Arrays.toString(msgs));
        } else {
            payloadTextView.setText("No raw msgs");
        }

        tagIdTextView.setText("Tag ID: " + Arrays.toString(tag.getId()));
    }
}
