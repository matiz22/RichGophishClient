package gophish.email_templates.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.DataOrError
import gophish.email_templates.composables.EmailTemplateCard
import template.domain.model.Template

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmailListTemplatesScreen(
    templates: DataOrError<List<Template>>,
    navigateToDetails: (Template) -> Unit,
) {
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