package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import java.util.*

class TimelineViewModel : ViewModel() {

    private val _timelineData = MutableLiveData<List<Task>>()
    val timelineData: LiveData<List<Task>> = _timelineData



    fun fakeData() =
        listOf(
            Task(
                TaskType.CALL,
                "Ligar para o Bruno da Aztechnology para falar sobre nossos produtos",
                "Bruno Henrique de Oliveira",
                Date()
            ),
            Task(
                TaskType.VISIT,
                "Visitar o cliente Black Star para verificar se estão precisando renovar seus suprimentos, " +
                        "e apresentar nossos novos produtos",
                "Black Star Ltda",
                Date()
            ),
            Task(
                TaskType.MAIL,
                "Enviar e-mail com as especificações do produto XPTO para análise técnica",
                "Industrias Torricelli",
                Date()
            ),
            Task(TaskType.PROPOSAL, "Propor implantação de sistema na loja", "André Souza", Date())
        )

}
