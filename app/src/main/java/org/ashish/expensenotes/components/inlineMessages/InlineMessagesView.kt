package org.ashish.expensenotes.components.inlineMessages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.ashish.expensenotes.R
import org.ashish.expensenotes.components.inlineMessages.data.InlineMessageColor
import org.ashish.expensenotes.components.inlineMessages.data.InlineMessageItemModel
import org.ashish.expensenotes.components.inlineMessages.data.InlineMessageModel
import org.ashish.expensenotes.components.inlineMessages.data.MessageSeverity

@Composable
fun InlineMessageView(errors: List<InlineMessageModel>) {
    Column {
        errors.forEach {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(getBackgroundColor(severity = it.severity))
                        .border(BorderStroke(1.dp, getBorderColor(severity = it.severity)))
                        .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    modifier = Modifier.padding(PaddingValues(end = 10.dp)),
                    painter = painterResource(getIconName(it.severity)),
                    contentDescription = null,
                )
                Text(text = it.message)
            }
        }
    }
}

@Composable
fun InlineMessageInfo(content: @Composable () -> Unit) {
    val severity = MessageSeverity.INFO.severity
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(getBackgroundColor(severity = severity))
                .border(BorderStroke(1.dp, getBorderColor(severity = severity)))
                .padding(10.dp),
    ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.padding(PaddingValues(end = 10.dp)),
                painter = painterResource(R.drawable.icon_info),
                contentDescription = null,
            )
            content()
        }

    }
}

@Composable
fun InlineMessageWarning(content: @Composable () -> Unit) {
    val severity = MessageSeverity.WARNING.severity
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(getBackgroundColor(severity = severity))
                .border(BorderStroke(1.dp, getBorderColor(severity = severity)))
                .padding(10.dp),
    ) {
        content.invoke()
    }
}

@Composable
fun InlineMessagesError(content: @Composable () -> Unit) {
    val severity = MessageSeverity.ERROR.severity
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(getBackgroundColor(severity = severity))
                .border(BorderStroke(1.dp, getBorderColor(severity = severity)))
                .padding(10.dp),
    ) {
        content.invoke()
    }
}

@Composable
fun InlineMessages(messages: List<InlineMessageItemModel>) {
    val grouped = messages.groupBy { it.severity }
    Column {
        grouped.forEach { (severity, messages) ->
            when (severity) {
                MessageSeverity.INFO.severity -> {
                    GroupedInlineMessagesBuilder(content = {
                        messages.forEach {
                            InlineMessageInfo {
                                it.content()
                            }
                        }
                    }, severity = MessageSeverity.INFO.severity)
                }

                MessageSeverity.ERROR.severity -> {
                    GroupedInlineMessagesBuilder(content = {
                        messages.forEach {
                            InlineMessagesError {
                                it.content()
                            }
                        }
                    }, severity = MessageSeverity.ERROR.severity)
                }

                MessageSeverity.WARNING.severity -> {
                    GroupedInlineMessagesBuilder(content = {
                        messages.forEach {
                            InlineMessageWarning {
                                it.content()
                            }
                        }
                    }, severity = MessageSeverity.WARNING.severity)
                }
            }
        }
    }
}

@Composable
fun GroupedInlineMessagesBuilder(
    content: @Composable () -> Unit,
    severity: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(getBackgroundColor(severity = severity))
                .border(BorderStroke(1.dp, getBorderColor(severity = severity)))
                .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        Icon(
            modifier = Modifier.padding(PaddingValues(end = 10.dp)),
            painter = painterResource(id = getIconName(severity)),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            content()
        }
    }
}

@Composable
fun InlineMessages(content: LazyListScope.() -> Unit) {
    LazyColumn(modifier = Modifier.heightIn(min = 100.dp, max = 300.dp)) {
        content()
    }
}

fun LazyListScope.InlineMessageItemError(content: @Composable () -> Unit) {
    item {
        InlineMessagesError(content = content)
    }
}

fun LazyListScope.InlineMessageItemWarning(content: @Composable () -> Unit) {
    item {
        InlineMessageWarning(content = content)
    }
}

fun LazyListScope.InlineMessageItemInfo(content: @Composable () -> Unit) {
    item {
        InlineMessageInfo(content = content)
    }
}

@Preview
@Composable
fun InlineMessagesPreview() {
    InlineMessages {
        InlineMessageItemInfo {
            Text(text = "test")
        }

        InlineMessageItemInfo {
            Text(text = "test")
        }
    }
}

@Composable
fun InlineMessageGroupView(errors: List<InlineMessageModel>) {
    val groupErrors = errors.groupBy { it.severity }
    groupErrors.forEach { (severity, errorList) ->
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(getBackgroundColor(severity = severity))
                    .border(BorderStroke(1.dp, getBorderColor(severity = severity))),
        ) {
            Column {
                errorList.forEach {
                    Row {
                        Text(text = it.message)
                    }
                }
            }
        }
    }
}

fun getIconName(severity: String): Int =
    when (severity) {
        MessageSeverity.ERROR.severity -> R.drawable.icon_error
        MessageSeverity.INFO.severity -> R.drawable.icon_info
        MessageSeverity.WARNING.severity -> R.drawable.warning
        MessageSeverity.SUCCESS.severity -> R.drawable.icon_info

        else -> {
            R.drawable.icon_info
        }
    }

@Composable
fun getBackgroundColor(severity: String): Color =
    when (severity) {
        MessageSeverity.ERROR.severity -> InlineMessageColor.c_inline_messages__error_background_color
        MessageSeverity.INFO.severity -> InlineMessageColor.c_inline_messages__information_background_color
        MessageSeverity.WARNING.severity -> InlineMessageColor.c_inline_messages__warning_background_color
        MessageSeverity.SUCCESS.severity -> InlineMessageColor.c_inline_messages__success_background_color
        else -> {
            Color.White
        }
    }

@Composable
fun getBorderColor(severity: String): Color =
    when (severity) {
        MessageSeverity.ERROR.severity -> InlineMessageColor.c_inline_messages__error_border_color
        MessageSeverity.INFO.severity -> InlineMessageColor.c_inline_messages__information_border_color
        MessageSeverity.WARNING.severity -> InlineMessageColor.c_inline_messages__warning_border_color
        MessageSeverity.SUCCESS.severity -> InlineMessageColor.c_inline_messages__success_border_color

        else -> {
            Color.White
        }
    }
