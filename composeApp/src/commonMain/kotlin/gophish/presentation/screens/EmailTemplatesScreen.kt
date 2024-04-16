package gophish.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.DataOrError
import gophish.domain.model.CreateTemplateForm
import gophish.presentation.composables.EmailTemplateCard
import template.domain.model.Template

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmailTemplatesScreen(
    templates: DataOrError<List<Template>>,
    form: CreateTemplateForm,
    navigateToDetails: (Template) -> Unit
) {
    if (form.isShownDialog) {

    }
    if (templates.data != null) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(4.dp),
            columns = GridCells.FixedSize(500.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(templates.data!!) { template ->
                EmailTemplateCard(
                    modifier = Modifier.width(500.dp),
                    template = template,
                    onClick = {
                        navigateToDetails(template)
                    }
                )
            }
        }
    }
}