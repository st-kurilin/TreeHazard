package ua.kharkov.kture.ot.view.declaration.additionalwindows;

import com.google.inject.assistedinject.Assisted;
import ua.kharkov.kture.ot.shared.navigation.places.ErrorMessagePlace;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public interface ErrorMessagesFactory {
    AdditionalWindow createWindow(@Assisted ErrorMessagePlace place);
}
