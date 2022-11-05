package com.bahar.gamescore.api;

import com.bahar.gamescore.util.ServiceName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlLoginComponents {
    String userIdentifier;
    ServiceName serviceName;
}
