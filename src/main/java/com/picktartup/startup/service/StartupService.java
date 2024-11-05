package com.picktartup.startup.service;

import java.util.List;

public interface StartupService {
    List<com.picktartup.startup.dto.StartupServiceRequest> getTop6StartupsByProgress();
}
