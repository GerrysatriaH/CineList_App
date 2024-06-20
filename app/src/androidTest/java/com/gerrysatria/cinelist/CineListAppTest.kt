package com.gerrysatria.cinelist

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.gerrysatria.cinelist.model.MovieDataSource
import com.gerrysatria.cinelist.ui.navigation.Screen
import com.gerrysatria.cinelist.ui.theme.CineListTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CineListAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CineListTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                CineListApp(navController = navController)
            }
        }
    }

    @Test
    fun test_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun test_clickItem_navigatesToDetailWithData() {
        // 1. Scroll list sampai menemukan item index ke-10.
        composeTestRule.onNodeWithTag("MovieList").performScrollToIndex(10)
        // 2. Klik item index ke-10.
        composeTestRule.onNodeWithText(MovieDataSource.dataMovie[10].name).performClick()
        // 3. Halaman Detail tampil.
        navController.assertCurrentRouteName(Screen.DetailMovie.route)
        // 4. Cek apakah judul movie pada Halaman Detail sama dengan judul pada item yang diklik.
        composeTestRule.onNodeWithText(MovieDataSource.dataMovie[10].name).assertIsDisplayed()
    }

    @Test
    fun test_bottomNavigation_working() {
        // 1. Halaman Home tampil.
        navController.assertCurrentRouteName(Screen.Home.route)
        // 2. Klik menu watchlist.
        composeTestRule.onNodeWithStringId(R.string.menu_watchlist).performClick()
        // 3. Halaman Watchlist tampil.
        navController.assertCurrentRouteName(Screen.Watchlist.route)
        // 4. Klik menu profile.
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        // 5. Halaman Profile tampil.
        navController.assertCurrentRouteName(Screen.About.route)
        // 6. Klik menu home.
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        // 7. Halaman Home tampil.
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun test_bottomAddToWatchlist_working() {
        // 1. Scroll list sampai menemukan item index ke-12.
        composeTestRule.onNodeWithTag("MovieList").performScrollToIndex(12)
        // 2. Klik item index ke-1.
        composeTestRule.onNodeWithText(MovieDataSource.dataMovie[12].name).performClick()
        // 3. Halaman Detail tampil.
        navController.assertCurrentRouteName(Screen.DetailMovie.route)
        // 4. Cek apakah judul movie pada Halaman Detail sama dengan judul pada item yang diklik.
        composeTestRule.onNodeWithText(MovieDataSource.dataMovie[12].name).assertIsDisplayed()
        // 5. Klik button Add to Watchlist.
        composeTestRule.onNodeWithContentDescription("Add To List Button").performClick()
        // 6. Halaman Watchlist tampil.
        navController.assertCurrentRouteName(Screen.Watchlist.route)
        // 7. Cek apakah nama movie yang ditambah tampil pada halaman watchlist
        composeTestRule.onNodeWithText(MovieDataSource.dataMovie[12].name).assertIsDisplayed()
    }

    @Test
    fun test_searchBar_working(){
        // 1. Memeriksa apabila search bar tampil
        composeTestRule.onNodeWithContentDescription("search").assertIsDisplayed()
        // 2. Memasukkan query = "Killer" pada search bar.
        composeTestRule.onNodeWithStringId(R.string.placeholder_search).performTextInput("Killer")
        // 3. Memastikan dataMovie[4] = "The Killer" tampil.
        composeTestRule.onNodeWithText(MovieDataSource.dataMovie[4].name).assertIsDisplayed()
        // 4. Memastikan dataMovie[3] = "A Haunting in Venice" tidak tampil.
        composeTestRule.onNodeWithText(MovieDataSource.dataMovie[3].name).assertIsNotDisplayed()
    }

    @Test
    fun test_searchBar_notFound(){
        // 1. Memeriksa apabila search bar tampil
        composeTestRule.onNodeWithContentDescription("search").assertIsDisplayed()
        // 2. Memasukkan query = "superman" pada search bar.
        composeTestRule.onNodeWithStringId(R.string.placeholder_search).performTextInput("superman")
        // 3. Memastikan pesan data tidak ditemukan tampil ketika hasil pencarian tidak ada.
        composeTestRule.onNodeWithStringId(R.string.search_message).assertIsDisplayed()
    }
}