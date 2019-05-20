package com.workday.words;

import com.workday.words.exceptions.QueryException;
import com.workday.words.interfaces.IQueryInformation;

public class MockQuery implements IQueryInformation {
    @Override
    public String getPageStream(String pageId) throws QueryException {
        return "Homer Davenport (1867–1912) was a political cartoonist and writer from the United States. He is known for drawings that satirized figures of the Gilded Age and Progressive Era, especially Ohio Senator Mark Hanna. Although Davenport had no formal art training, he became one of the highest paid political cartoonists in the world. He was also one of the first major American breeders of Arabian horses and one of the founders of the Arabian Horse Club of America. In 1893 he studied and drew the Arabian horses exhibited at the World's Columbian Exposition. In 1904 he drew a favorable cartoon of President Theodore Roosevelt that boosted Roosevelt's election campaign. The president in turn helped Davenport in 1906 when";
    }
}
