package com.example.careerpathcalculator

object CareerSuggestionEngine {

    private val techKeywords = listOf("computer", "programming", "technology", "code", "software", "game", "internet", "app", "digital")
    private val scienceKeywords = listOf("science", "math", "physics", "chemistry", "biology", "research", "lab", "experiment", "nature")
    private val artsKeywords = listOf("art", "design", "music", "write", "creative", "draw", "paint", "perform", "film", "fashion")
    private val businessKeywords = listOf("business", "economics", "money", "market", "trade", "sell", "lead", "manage", "entrepreneur")

    private val techCareers = listOf(
        CareerSuggestion(
            "Software Developer",
            "Create applications that solve real-world problems through code.",
            "Code is poetry with purpose.",
            R.drawable.icon_developer,
            "TECH"
        ),
        CareerSuggestion(
            "Data Scientist",
            "Extract insights from vast amounts of data to drive decisions.",
            "Data is the new oil, and you're the refiner.",
            R.drawable.icon_data,
            "TECH"
        ),
        CareerSuggestion(
            "UX Designer",
            "Create intuitive and engaging user experiences for digital products.",
            "Design is not just what it looks like, it's how it works.",
            R.drawable.icon_developer,
            "TECH"
        ),
        CareerSuggestion(
            "Cybersecurity Expert",
            "Protect organizations from digital threats and vulnerabilities.",
            "In the digital world, you are the guardian at the gate.",
            R.drawable.icon_data,
            "TECH"
        )
    )

    private val scienceCareers = listOf(
        CareerSuggestion(
            "Research Scientist",
            "Push the boundaries of human knowledge through rigorous study.",
            "The joy of discovery awaits those who question.",
            R.drawable.icon_science,
            "SCIENCE"
        ),
        CareerSuggestion(
            "Healthcare Professional",
            "Impact lives directly through medicine and healthcare.",
            "Healing is not just science, but an art of compassion.",
            R.drawable.icon_healthcare,
            "SCIENCE"
        ),
        CareerSuggestion(
            "Environmental Scientist",
            "Study the environment to protect and preserve our planet.",
            "Nature's complexity is your laboratory.",
            R.drawable.icon_science,
            "SCIENCE"
        ),
        CareerSuggestion(
            "Aerospace Engineer",
            "Design aircraft, spacecraft, satellites, and missiles.",
            "The sky is not the limit when your goal is the stars.",
            R.drawable.icon_healthcare,
            "SCIENCE"
        )
    )

    private val artsCareers = listOf(
        CareerSuggestion(
            "Graphic Designer",
            "Communicate ideas visually and create meaningful experiences.",
            "Design is intelligence made visible.",
            R.drawable.icon_design,
            "ARTS"
        ),
        CareerSuggestion(
            "Content Creator",
            "Share stories and ideas through various media channels.",
            "Your voice has the power to inspire generations.",
            R.drawable.icon_content,
            "ARTS"
        ),
        CareerSuggestion(
            "Fashion Designer",
            "Create clothing and accessories that blend beauty and function.",
            "Fashion is architecture: it's a matter of proportions.",
            R.drawable.icon_design,
            "ARTS"
        ),
        CareerSuggestion(
            "Music Producer",
            "Create and shape sounds that move people emotionally.",
            "Music is the universal language that speaks to every soul.",
            R.drawable.icon_content,
            "ARTS"
        )
    )

    private val businessCareers = listOf(
        CareerSuggestion(
            "Entrepreneur",
            "Build ventures that solve problems and create value.",
            "The best way to predict the future is to create it.",
            R.drawable.icon_entrepreneur,
            "BUSINESS"
        ),
        CareerSuggestion(
            "Marketing Specialist",
            "Connect products and services with the people who need them.",
            "Great marketing makes the company look smart, not the marketing.",
            R.drawable.icon_marketing,
            "BUSINESS"
        ),
        CareerSuggestion(
            "Financial Analyst",
            "Evaluate financial data to guide investment decisions.",
            "Numbers tell a story for those who know how to read them.",
            R.drawable.icon_entrepreneur,
            "BUSINESS"
        ),
        CareerSuggestion(
            "Human Resources Manager",
            "Build and support teams that drive organizational success.",
            "Great workplaces are built on great people.",
            R.drawable.icon_marketing,
            "BUSINESS"
        )
    )

    fun getSuggestion(interests: String, subject: String, hobby: String): CareerSuggestion {
        val combinedInput = "$interests $subject $hobby".toLowerCase()

        // Count matches for each category
        val techScore = techKeywords.count { combinedInput.contains(it) }
        val scienceScore = scienceKeywords.count { combinedInput.contains(it) }
        val artsScore = artsKeywords.count { combinedInput.contains(it) }
        val businessScore = businessKeywords.count { combinedInput.contains(it) }

        // Find best match
        val scores = mapOf(
            "TECH" to techScore,
            "SCIENCE" to scienceScore,
            "ARTS" to artsScore,
            "BUSINESS" to businessScore
        )

        val topCategory = scores.maxByOrNull { it.value }?.key ?: "TECH"

        // Get random career from top category
        return when (topCategory) {
            "TECH" -> techCareers.random()
            "SCIENCE" -> scienceCareers.random()
            "ARTS" -> artsCareers.random()
            "BUSINESS" -> businessCareers.random()
            else -> techCareers.first() // Default fallback
        }
    }
}