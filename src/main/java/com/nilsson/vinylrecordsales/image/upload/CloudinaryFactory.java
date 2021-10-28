package com.nilsson.vinylrecordsales.image.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nilsson.vinylrecordsales.domain.ApiToken;
import org.springframework.core.env.Environment;

import static com.nilsson.vinylrecordsales.image.upload.CloudinaryFactory.CloudinaryConfigProperty.API_KEY;
import static com.nilsson.vinylrecordsales.image.upload.CloudinaryFactory.CloudinaryConfigProperty.CLOUD_NAME;
import static java.util.Objects.requireNonNull;

public class CloudinaryFactory {

    private static final String CLOUD_NAME_KEY = "cloud_name";
    private static final String API_KEY_STR = "api_key";
    private static final String API_SECRET_KEY = "api_secret";
    private static final String GENERATE_HTTPS_URL = "secure";
    private final ApiToken apiToken;
    private final String cloudName;
    private final String apiKey;

    public CloudinaryFactory(Environment environment, ApiToken apiToken) {
        requireNonNull(environment, "environment");
        this.apiToken = requireNonNull(apiToken, "apiToken");
        this.cloudName = requireNonNull(environment.getRequiredProperty(CLOUD_NAME.value));
        this.apiKey = requireNonNull(environment.getRequiredProperty(API_KEY.value));

    }

    public Cloudinary get() {
        return new Cloudinary(ObjectUtils.asMap(
                CLOUD_NAME_KEY, cloudName,
                API_KEY_STR, apiKey,
                API_SECRET_KEY, apiToken.getToken(),
                GENERATE_HTTPS_URL, true));
    }

    enum CloudinaryConfigProperty {
        CLOUD_NAME("image.upload.cloudinary.cloudname"),
        API_KEY("image.upload.cloudinary.api.key");


        public final String value;

        CloudinaryConfigProperty(String value) {

            this.value = value;
        }
    }
}
