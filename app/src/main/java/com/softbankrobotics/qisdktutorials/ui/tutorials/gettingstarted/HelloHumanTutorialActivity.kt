/*
 * Copyright (C) 2018 Softbank Robotics Europe
 * See COPYING for the license
 */

package com.softbankrobotics.qisdktutorials.ui.tutorials.gettingstarted

import android.os.Bundle

import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.builder.TopicBuilder
import com.softbankrobotics.qisdktutorials.R
import com.softbankrobotics.qisdktutorials.ui.conversation.ConversationBinder
import com.softbankrobotics.qisdktutorials.ui.tutorials.TutorialActivity
import kotlinx.android.synthetic.main.conversation_layout.*

/**
 * The activity for the Hello human tutorial.
 */
class HelloHumanTutorialActivity : TutorialActivity(), RobotLifecycleCallbacks {
    private var conversationBinder: ConversationBinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this)
    }

    override fun onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override val layoutId = R.layout.conversation_layout

    override fun onRobotFocusGained(qiContext: QiContext) {
        // Bind the conversational events to the view.
        val conversationStatus = qiContext.conversation.status(qiContext.robotContext)
        conversationBinder = conversation_view.bindConversationTo(conversationStatus)

        // Create a new say action.
        val say = SayBuilder.with(qiContext) // Create the builder with the context.
                .withText("Hello human!") // Set the text to say.
                .build() // Build the say action.

        // Execute the action.
        say.run()
    }

    override fun onRobotFocusLost() {
        conversationBinder?.unbind()
    }

    override fun onRobotFocusRefused(reason: String) {
        // Nothing here.
    }
}

//        Todo: hier kan dus ook een topic builder in. Neem deze file als template voor nieuwe totirals/subprogramma's i.c.m.:
//             QiChatbotTutorialActivity is ook een goede template. Kijk vooral in het mapje van tutorials, conversation.

//class 'class file name' : TutorialActivity(), RobotLifecycleCallbacks {
// override fun onRobotFocusGained(qiContext: QiContext) {
//val commonTopic = TopicBuilder.with(qiContext)
//    .withResource(R.raw.common)
//    .build()
//
//val qiChatbot = QiChatbotBuilder.with(qiContext)
//    .withTopics(listOf(commonTopic, talkTopic, moveTopic, smartTopic))
//    .build()
//    .also { this.qiChatbot = it }
//
//val chat = ChatBuilder.with(qiContext)
//    .withChatbot(qiChatbot)
//    .build()
//
//qiChatbot.addOnEndedListener { presenter.goToTutorialForQiChatbotId(it) }
//this.qiChatbot = qiChatbot
//chatFuture = chat.async().run()