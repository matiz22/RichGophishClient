package gophish.campaign.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.campaign.composables.PickerCampaignDetails
import gophish.campaign.events.CreateCampaignEvent
import gophish.campaign.state.CampaignFormState


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateCampaignScreen(
    form: CampaignFormState,
    pages: List<String>,
    groups: List<String>,
    templates: List<String>,
    onEvent: (CreateCampaignEvent) -> Unit,
    smtps: List<String>
) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.name,
                onValueChange = {
                    onEvent(CreateCampaignEvent.UpdateName(it))
                },
                hintText = AppRes.string.campaign_hint
            )
        }

        item {
            PickerCampaignDetails(
                pickedOption = form.template,
                options = templates,
                pickOption = {
                    onEvent(CreateCampaignEvent.UpdateTemplate(it))
                },
                hint = AppRes.string.campaign_hint_template
            )
        }

        item {
            PickerCampaignDetails(
                pickedOption = form.page,
                options = pages,
                pickOption = {
                    onEvent(CreateCampaignEvent.UpdatePage(it))
                },
                hint = AppRes.string.campaign_hint_page
            )
        }

        item {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.url,
                onValueChange = {
                    onEvent(CreateCampaignEvent.UpdateUrl(it))
                },
                hintText = AppRes.string.campaign_hint_url
            )
        }

        item {
            PickerCampaignDetails(
                pickedOption = form.smtp,
                options = smtps,
                pickOption = {
                    onEvent(CreateCampaignEvent.UpdateSmtp(it))
                },
                hint = AppRes.string.campaign_hint_groups
            )
        }

        item {
            PickerCampaignDetails(
                pickedOption = "",
                options = groups,
                pickOption = {
                    onEvent(CreateCampaignEvent.AddGroup(it))
                },
                hint = AppRes.string.campaign_hint_groups
            )
        }

        item {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                form.pickedGroups.forEach { group ->
                    OutlinedCard(
                        onClick = {
                            onEvent(CreateCampaignEvent.DeleteGroup(group))
                        }
                    ) {
                        Box(modifier = Modifier.padding(8.dp)) {
                            Text(text = group)
                        }
                    }
                }
            }
        }
        item {
            Button(onClick = { onEvent(CreateCampaignEvent.Submit) }) {
                Text(text = AppRes.string.submit_button)
            }
        }
    }
}