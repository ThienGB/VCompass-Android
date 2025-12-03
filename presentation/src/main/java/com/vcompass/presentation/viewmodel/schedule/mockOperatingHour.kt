package com.vcompass.presentation.viewmodel.schedule

import com.vcompass.presentation.model.business.Business
import com.vcompass.presentation.model.business.ContactInformation
import com.vcompass.presentation.model.business.Location
import com.vcompass.presentation.model.business.attraction.OperatingHour
import com.vcompass.presentation.model.schedule.Activity
import com.vcompass.presentation.model.schedule.AdditionalExpense
import com.vcompass.presentation.model.schedule.DayActivity
import com.vcompass.presentation.model.schedule.Schedule

// --- 1. Dữ liệu Phụ Trợ (Supporting Data) ---

val mockOperatingHour = OperatingHour(
    startDay = "Thứ Hai",
    endDay = "Chủ Nhật",
    openTime = "08:00",
    closeTime = "21:00"
)

val mockContactInformation = ContactInformation(
    phone = "02633831343",
    email = "contact@dalattrip.com",
    website = "https://www.dulichdalat.vn"
)


val mockBusiness = Business(
    partnerId = "B001",
    name = "Quán Cà Phê Túi Mơ To",
    description = "Quán cà phê săn mây nổi tiếng với view thung lũng tuyệt đẹp.",
    location = Location(latitude = 11.9774, longitude = 108.4350, address = "Túi Mơ To, Phường 11, Đà Lạt"),
    images = listOf("image_view_1.jpg", "image_view_2.jpg"),
    amenities = listOf("Wifi miễn phí", "Bãi đậu xe"),
    contact = mockContactInformation,
    registerDate = "2023-01-15",
    status = "Active",
    averageRating = 4.5f,
    totalRatings = 1200f,
    operatingHours = listOf(mockOperatingHour)
)

val mockAdditionalExpense = AdditionalExpense(
    cost = 500000,
    description = "Chi phí xăng xe di chuyển chung"
)

// --- 2. Dữ liệu Hoạt động (Activity Data) ---

val mockActivityDay1 = Activity(
    activityType = "Sightseeing",
    idDestination = "D001",
    name = "Check-in Quảng trường Lâm Viên",
    address = "Quảng trường Lâm Viên, Phường 10, Đà Lạt",
    imgSrc = listOf("lamvien_1.jpg"),
    cost = 0,
    costDescription = "Miễn phí",
    description = "Chụp ảnh tại Nụ Hoa Atisô và Bông Hoa Dã Quỳ khổng lồ.",
    timeStart = "15:00",
    timeEnd = "16:00",
    business = Business(name = "Quảng trường Lâm Viên") // Dùng Business đơn giản hơn
)

val mockActivityDay2 = Activity(
    activityType = "FOODPLACE",
    idDestination = "D002",
    name = "Uống cà phê săn mây",
    address = "Túi Mơ To, Phường 11, Đà Lạt",
    imgSrc = listOf("cf_tuimoto_1.jpg"),
    cost = 70000,
    costDescription = "Giá trung bình 1 ly",
    description = "Thưởng thức cà phê và ngắm cảnh mây mù buổi sáng.",
    timeStart = "07:30",
    timeEnd = "09:00",
    business = mockBusiness // Dùng Business chi tiết đã tạo ở trên
)

// --- 3. Dữ liệu Ngày (DayActivity Data) ---

val mockDay1 = DayActivity(
    day = 1,
    activity = listOf(mockActivityDay1) // Có thể thêm nhiều Activity hơn
)

val mockDay2 = DayActivity(
    day = 2,
    activity = listOf(mockActivityDay2)
)

// --- 4. Dữ liệu Lịch trình Chính (Schedule Data) ---

val mockSchedule: Schedule = Schedule(
    // user: UserModel? = null, // Giả định UserModel đã được định nghĩa ở nơi khác
    name = "Hành trình Khám phá Đà Lạt 3 Ngày",
    description = "Chuyến đi 3 ngày 2 đêm khám phá các địa điểm check-in nổi tiếng và trải nghiệm ẩm thực Đà Lạt.",
    departure = "TP. Hồ Chí Minh",
    destinations = listOf("Đà Lạt", "Lâm Đồng"),
    numDays = 3,
    dateStart = "2025-12-01",
    dateEnd = "2025-12-03",
    video = "youtube_link_dalat_vlog",
    images = listOf("cover_dalat_1.jpg", "cover_dalat_2.jpg"),
    types = listOf("Du lịch", "Check-in", "Nghỉ dưỡng"),
    isPublic = true,
    status = "Active",
    tags = listOf("Đà Lạt", "Dulich", "Vietnam"),
    idInvited = listOf("userA", "userB"),
    days = listOf(mockDay1, mockDay2), // Có thể thêm mockDay3
    additionalExpenses = listOf(mockAdditionalExpense),
    // comments: List<Comment>? = null, // Giả định Comment và Like là rỗng
    // likes: List<Like>? = null
)