package com.jeremieguillot.identityreader.nfc.presentation.reader.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jeremieguillot.identityreader.R
import com.jeremieguillot.identityreader.nfc.domain.NfcReaderStatus

@Composable
fun NfcReaderStatus.getTitle(): String {
    return when (this) {
        NfcReaderStatus.IDLE -> stringResource(id = R.string.nfc_title_idle)
        NfcReaderStatus.CONNECTING -> stringResource(id = R.string.nfc_title_connecting)
        NfcReaderStatus.CONNECTED -> stringResource(id = R.string.nfc_title_connected)
        NfcReaderStatus.DISCONNECTED -> stringResource(id = R.string.nfc_title_disconnected)
        NfcReaderStatus.ERROR -> stringResource(id = R.string.nfc_title_error)
    }
}

@Composable
fun NfcReaderStatus.getDescription(): String {
    return when (this) {
        NfcReaderStatus.IDLE -> stringResource(id = R.string.nfc_desc_idle)
        NfcReaderStatus.CONNECTING -> stringResource(id = R.string.nfc_desc_connecting)
        NfcReaderStatus.CONNECTED -> stringResource(id = R.string.nfc_desc_connected)
        NfcReaderStatus.DISCONNECTED -> stringResource(id = R.string.nfc_desc_disconnected)
        NfcReaderStatus.ERROR -> stringResource(id = R.string.nfc_desc_error)
    }
}
