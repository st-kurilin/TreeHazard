package ua.kharkov.kture.ot.common.localization;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class FakeMessageBundle implements MessageBundle {
    @Override
    public String getMessage(String key) {
        return key;
    }
}
