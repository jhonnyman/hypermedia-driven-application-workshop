package ch.construo.hdaworkshop;

import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import io.quarkiverse.playwright.InjectPlaywright;
import io.quarkiverse.playwright.WithPlaywright;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@WithPlaywright
@QuarkusTest
class EagerLoadingTest {

    @InjectPlaywright
    BrowserContext context;

    @TestHTTPResource("/eager")
    URL eagerPage;

    @Test
    void testDeleteCedent() {
        final var page = context.newPage();
        page.setDefaultTimeout(3000);
        Response response = page.navigate(eagerPage.toString());
        Assertions.assertEquals("OK", response.statusText());
        var bobCedent = page.locator("#cedent-table").getByText("Bob");
        PlaywrightAssertions.assertThat(bobCedent).isVisible();
        var deleteButton = page.getByTestId("delete-Bob");
        deleteButton.click();
        PlaywrightAssertions.assertThat(bobCedent).not().isVisible();
    }

    @Test
    void browserOpened_navigatesToEagerPage_rendersCorrectly() {
        final var page = context.newPage();
        Response response = page.navigate(eagerPage.toString());
        Assertions.assertEquals("OK", response.statusText());

        PlaywrightAssertions.assertThat(page.getByText("Eager Loading Demo")).isVisible();
        PlaywrightAssertions.assertThat(page.getByText("Our Cedents")).isVisible();
        PlaywrightAssertions.assertThat(page.getByText("Claims published")).isVisible();
    }
}
