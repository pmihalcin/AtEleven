package no.itera.ateleven.repository

import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.Food
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import java.util.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(TestApplication::class))
@WebAppConfiguration
class DailyMenuRepositoryTest {

    @Autowired lateinit var dailyMenuRepository: DailyMenuRepository

    @After
    fun after() {
        dailyMenuRepository.deleteAll()
    }

    @Test
    fun shouldSaveDailyMenu() {
        var dailyMenu = TestApplication.dailyMenuMock()

        dailyMenu = dailyMenuRepository.save(dailyMenu)

        assertNotNull(dailyMenu)
        assertNotNull(dailyMenu.id)
    }

    @Test(expected = Exception::class)
    fun shouldNotSaveSameMenuForRestaurantAndDay() {
        val firstDailyMenu = DailyMenu(
                null,
                "YetAnotherRestaurant",
                "2016-01-20",
                Arrays.asList(Food("Mrkvova", ""), Food("Cicerova", "")),
                Arrays.asList(Food("Hovadzi gulas", ""), Food("Slovenske rizoto", "")),
                emptyList<Food>())

        val secondDailyMenu = DailyMenu(
                null,
                "YetAnotherRestaurant",
                "2016-01-20",
                Arrays.asList(Food("Mrkvova", ""), Food("Cicerova", "")),
                Arrays.asList(Food("Hovadzi gulas", ""), Food("Slovenske rizoto", "")),
                emptyList<Food>())

        dailyMenuRepository.save(firstDailyMenu)
        dailyMenuRepository.save(secondDailyMenu)
    }

}