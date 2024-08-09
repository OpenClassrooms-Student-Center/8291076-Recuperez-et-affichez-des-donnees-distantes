package com.jeremieguillot.identityreader.nfc.data

import android.nfc.Tag
import android.nfc.tech.IsoDep
import com.jeremieguillot.identityreader.core.domain.DataDocument
import com.jeremieguillot.identityreader.core.domain.IdentityDocument
import com.jeremieguillot.identityreader.core.domain.MRZ
import com.jeremieguillot.identityreader.core.domain.util.Error
import com.jeremieguillot.identityreader.core.domain.util.Result
import com.jeremieguillot.identityreader.nfc.domain.NfcReaderStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class NFCReader(dataDocument: DataDocument) {

    private var mrz: MRZ = MRZ(
        dataDocument.documentNumber,
        dataDocument.dateOfBirth,
        dataDocument.dateOfExpiry
    )
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _status: MutableStateFlow<NfcReaderStatus> = MutableStateFlow(NfcReaderStatus.IDLE)
    val status: MutableStateFlow<NfcReaderStatus> = _status

    fun update(newMRZ: MRZ) {
        mrz = newMRZ
        reset()
    }

    fun reset() {
        scope.launch {
            _status.emit(NfcReaderStatus.IDLE)
        }
    }

    fun onTagDiscovered(tag: Tag?): Result<IdentityDocument, Error> {
        scope.launch {
            _status.emit(NfcReaderStatus.CONNECTING)
        }
        val result = NFCDocument().startReadTask(IsoDep.get(tag), mrz)
        scope.launch {
            when (result) {
                is Result.Error -> _status.emit(NfcReaderStatus.ERROR)
                is Result.Success -> _status.emit(NfcReaderStatus.CONNECTED)
            }
        }
        return result
    }
}