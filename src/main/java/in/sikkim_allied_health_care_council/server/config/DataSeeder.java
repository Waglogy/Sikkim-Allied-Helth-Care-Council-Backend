package in.sikkim_allied_health_care_council.server.config;

import in.sikkim_allied_health_care_council.server.models.members.Category;
import in.sikkim_allied_health_care_council.server.models.members.CategoryType;
import in.sikkim_allied_health_care_council.server.models.members.Members;
import in.sikkim_allied_health_care_council.server.repositories.CategoryRepository;
import in.sikkim_allied_health_care_council.server.repositories.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Seeds the council roster (executive + professional categories and their
 * members) the first time the application starts against an empty database.
 * Photos are left blank for admins to upload.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public DataSeeder(CategoryRepository categoryRepository, MemberRepository memberRepository) {
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        if (categoryRepository.count() > 0) {
            return; // already seeded / has data
        }

        /* ------------------------------ Executive ----------------------------- */

        Category officeBearers = category("Office Bearers", 1, CategoryType.EXECUTIVE);
        member("Mr. Ramesh Dahal", "Chairperson",
                "Sr. Technical Officer, STNM Multispeciality Hospital, Gangtok", 1, officeBearers);

        Category exOfficio = category("Ex-Officio Members", 2, CategoryType.EXECUTIVE);
        member("Director (Administration) / (Medical Education)", "Ex-Officio Member",
                "Health & Family Welfare Department, Government of Sikkim, Gangtok", 1, exOfficio);
        member("Dr. Muralidhar V. Pai", "Dean",
                "Sikkim Manipal Institute of Medical Sciences, Tadong, Gangtok", 2, exOfficio);
        member("Dr. Nikita Joshi", "Principal",
                "Sikkim Manipal College of Physiotherapy, Gangtok", 3, exOfficio);

        /* ---------------------------- Professional ---------------------------- */

        Category c1 = category("Medical Laboratory & Life Sciences", 1, CategoryType.PROFESSIONAL);
        member("Ms. Chokie Lepcha", "Sr. Medical Lab Technologist",
                "Department of Pathology, STNM Multispeciality Hospital, Gangtok", 1, c1);
        member("Ms. Tezina Sharma", "Medical Lab Technologist",
                "SRL HIV Testing Lab, Department of Microbiology, STNM Multispeciality Hospital, Gangtok", 2, c1);

        Category c2 = category("Trauma, Burn Care & Surgical / Anaesthesia-related Technology", 2, CategoryType.PROFESSIONAL);
        member("Mr. Tapan Pourel", "Operation Theatre Technologist",
                "Department of Anesthesiology, STNM Multispeciality Hospital, Gangtok", 1, c2);

        Category c3 = category("Physiotherapy", 3, CategoryType.PROFESSIONAL);
        member("Dr. Binita Basnet (PT)", "Chief Physiotherapist cum HOD",
                "Department of Physiotherapy, STNM Multispeciality Hospital, Gangtok", 1, c3);
        member("Dr. Pramod Kumar Pradhan (PT)", "Sr. Physiotherapist",
                "Department of Physiotherapy, STNM Multispeciality Hospital, Gangtok", 2, c3);

        Category c4 = category("Nutrition Science", 4, CategoryType.PROFESSIONAL);
        member("Ms. Priya Sharma", "Chief Dietician cum HOD",
                "Department of Dietitics and Nutrition, STNM Multispeciality Hospital, Gangtok", 1, c4);
        member("Ms. Lalita Thapa", "Sr. Dietician",
                "Department of Dietitics and Nutrition, STNM Multispeciality Hospital, Gangtok", 2, c4);

        Category c5 = category("Ophthalmic Sciences", 5, CategoryType.PROFESSIONAL);
        member("Mr. Mukti Nath Dhamala", "Optometrist",
                "Department of Ophthalmology, Rangpo PHC", 1, c5);
        member("Mr. Subash Chandra Basnet", "Sr. Ophthalmic Technician",
                "Department of Ophthalmology, STNM Multispeciality Hospital, Gangtok", 2, c5);

        // Both positions currently vacant — category kept with no members.
        category("Occupational Therapy", 6, CategoryType.PROFESSIONAL);

        Category c7 = category("Community Care, Behavioural Health Sciences & Other Professionals", 7, CategoryType.PROFESSIONAL);
        member("Mr. Kishor Thapa", "Deputy Director (IEC) / Health Educator",
                "Department of Health & Family Welfare, Government of Sikkim, Gangtok", 1, c7);
        member("Mr. Sonam Gyaltsen Bhutia", "Deputy Director (IEC) / Health Educator",
                "Department of Health & Family Welfare, Government of Sikkim, Gangtok", 2, c7);

        Category c8 = category("Medical Radiology, Imaging & Therapeutic Technology", 8, CategoryType.PROFESSIONAL);
        member("Mr. Sailesh Basnet", "Sr. Radiographer",
                "Department of Radiology and Imaging, STNM Multispeciality, Gangtok", 1, c8);
        member("Mr. Dipen Gurung", "Medical Imaging Technologist",
                "Department of Radiology and Imaging, STNM Multispeciality, Gangtok", 2, c8);

        Category c9 = category("Medical Technologists & Physician Associate / Biomedical & Medical Equipment Technology", 9, CategoryType.PROFESSIONAL);
        member("Mr. Ravi Chauhan", "Sr. Biomedical Engineer",
                "Department of Biomedical, STNM Multispeciality Hospital, Gangtok", 1, c9);
        member("Mr. Kiran Rai", "Cardiovascular Technologist",
                "Department of Cardiology, District Hospital Namchi", 2, c9);

        Category c10 = category("Health Information Management & Health Informatics", 10, CategoryType.PROFESSIONAL);
        member("Mr. Shyam Kumar Limboo", "Medical Record Technologist",
                "Department of MRD, STNM Multispeciality Hospital, Gangtok", 1, c10);
    }

    private Category category(String name, int priority, CategoryType type) {
        return categoryRepository.save(Category.builder()
                .name(name)
                .priority(priority)
                .type(type)
                .build());
    }

    private void member(String name, String designation, String description, int priority, Category category) {
        memberRepository.save(Members.builder()
                .name(name)
                .designation(designation)
                .profileDescription(description)
                .orderPriority(priority)
                .category(category)
                .build());
    }
}
