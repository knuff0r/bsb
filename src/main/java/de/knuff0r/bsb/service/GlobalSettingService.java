package de.knuff0r.bsb.service;


import de.knuff0r.bsb.domain.GlobalSetting;

public interface GlobalSettingService {

    GlobalSetting save(GlobalSetting setting);

    GlobalSetting getSetting(String name);

    void delete(GlobalSetting setting);

    void delete(String name);

    Iterable<GlobalSetting> getAll();


}
