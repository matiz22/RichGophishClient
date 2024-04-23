package gophish.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.DataOrError
import gophish.presentation.composables.EmailTemplateCard
import gophish.presentation.composables.PageCard
import page.domain.model.Page
import template.domain.model.Template

@Composable
fun PagesListScreen(
    pages: DataOrError<List<Page>>,
    navigateToDetails: (Page) -> Unit,
) {
    if (pages.data != null) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(4.dp),
            columns = GridCells.FixedSize(500.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(pages.data!!) { page ->
                PageCard(
                    modifier = Modifier.width(500.dp),
                    template = page,
                    onClick = {
                        navigateToDetails(page)
                    }
                )
            }
        }
    }
}