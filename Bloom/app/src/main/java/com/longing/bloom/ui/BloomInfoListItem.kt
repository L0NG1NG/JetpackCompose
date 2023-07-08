package com.longing.bloom.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longing.bloom.ui.theme.BloomTheme
import com.longing.bloom.ui.theme.body1
import com.longing.bloom.ui.theme.gray
import com.longing.bloom.ui.theme.h2

@Composable
fun BloomInfoListItem(item: Pair<String, Int>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = item.second),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Column {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.first,
                        style = h2,
                        color = gray,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp)
                    )
                    Text(
                        text = "This is description",
                        style = body1,
                        color = gray,
                        modifier = Modifier.paddingFromBaseline(bottom = 24.dp)
                    )
                }

                var checkState by remember {
                    mutableStateOf(false)
                }
                Checkbox(
                    checked = checkState, onCheckedChange = { isChecked ->
                        checkState = isChecked
                    },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.secondary)
                )


            }
            Divider(Modifier.padding(end = 8.dp))
        }
    }

}

@Preview
@Composable
fun BloomInfoListItemPrev() {
    BloomTheme {
        BloomInfoListItem(item = bloomInfoList.first())
    }

}