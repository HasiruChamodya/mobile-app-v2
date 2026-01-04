package com.shanalanka.emergency.data.source

import com.shanalanka.emergency.data.local.entity.EmergencyGuideEntity
import com.shanalanka.emergency.data.local.entity.GuideStepEntity
import com.shanalanka.emergency.data.local.entity.GuideWarningEntity

/**
 * Pre-populated emergency guide data.
 * This data is used to pre-populate the database on first app launch.
 * 
 * All guides include step-by-step instructions, warnings, and when to call emergency.
 * Icon resources use Material Icons (android.R.drawable references are placeholders).
 */
object EmergencyGuidesData {
    
    fun getGuides(): List<EmergencyGuideEntity> {
        return listOf(
            // CPR - Adult
            EmergencyGuideEntity(
                id = "cpr_adult",
                title = "CPR - Adult",
                category = "BREATHING",
                description = "Cardiopulmonary resuscitation for adults (over 8 years)",
                whenToCallEmergency = "Call 119 immediately if person is unresponsive and not breathing normally.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // CPR - Child
            EmergencyGuideEntity(
                id = "cpr_child",
                title = "CPR - Child",
                category = "BREATHING",
                description = "Cardiopulmonary resuscitation for children (1-8 years)",
                whenToCallEmergency = "Call 119 immediately if child is unresponsive and not breathing normally.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Choking
            EmergencyGuideEntity(
                id = "choking",
                title = "Choking - Heimlich Maneuver",
                category = "BREATHING",
                description = "First aid for a person who is choking",
                whenToCallEmergency = "Call 119 if the person cannot breathe, cough, or speak, or if the obstruction cannot be cleared.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Snake Bites
            EmergencyGuideEntity(
                id = "snake_bite",
                title = "Snake Bite First Aid",
                category = "ENVIRONMENTAL",
                description = "First aid treatment for venomous snake bites",
                whenToCallEmergency = "Call 119 immediately for all snake bites, especially if symptoms appear.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Bleeding Control
            EmergencyGuideEntity(
                id = "bleeding_control",
                title = "Severe Bleeding Control",
                category = "INJURIES",
                description = "How to control severe bleeding and prevent shock",
                whenToCallEmergency = "Call 119 immediately for severe bleeding that doesn't stop after 10 minutes of pressure.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Burns
            EmergencyGuideEntity(
                id = "burns",
                title = "Burn Treatment",
                category = "INJURIES",
                description = "First aid for burns of different degrees",
                whenToCallEmergency = "Call 119 for large burns, burns on face/hands/feet/genitals, or third-degree burns.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Fractures
            EmergencyGuideEntity(
                id = "fractures",
                title = "Fracture First Aid",
                category = "INJURIES",
                description = "How to immobilize and support broken bones",
                whenToCallEmergency = "Call 119 for severe fractures, suspected spinal injury, or if bone is visible.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Heart Attack
            EmergencyGuideEntity(
                id = "heart_attack",
                title = "Heart Attack Response",
                category = "MEDICAL",
                description = "Recognizing and responding to a heart attack",
                whenToCallEmergency = "Call 119 immediately if you suspect a heart attack. Time is critical.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Stroke
            EmergencyGuideEntity(
                id = "stroke",
                title = "Stroke - FAST Method",
                category = "MEDICAL",
                description = "Recognizing stroke symptoms using the FAST method",
                whenToCallEmergency = "Call 119 immediately if you suspect a stroke. Every minute counts.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Drowning
            EmergencyGuideEntity(
                id = "drowning",
                title = "Drowning Rescue",
                category = "ENVIRONMENTAL",
                description = "Rescue and resuscitation for drowning victims",
                whenToCallEmergency = "Call 119 immediately after securing the victim from water.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            
            // Poisoning
            EmergencyGuideEntity(
                id = "poisoning",
                title = "Poisoning Response",
                category = "MEDICAL",
                description = "First aid for common poisoning situations",
                whenToCallEmergency = "Call 119 or poison control immediately if poisoning is suspected.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            )
        )
    }
    
    fun getSteps(): List<GuideStepEntity> {
        return listOf(
            // CPR Adult Steps
            GuideStepEntity(0, "cpr_adult", 1, "Check Responsiveness", "Tap the person's shoulder and shout. Check if they respond. Look for normal breathing."),
            GuideStepEntity(0, "cpr_adult", 2, "Call Emergency", "Call 119 or have someone else call. If alone, call before starting CPR."),
            GuideStepEntity(0, "cpr_adult", 3, "Position Person", "Lay the person flat on their back on a firm surface. Clear the area around them."),
            GuideStepEntity(0, "cpr_adult", 4, "Hand Position", "Place the heel of one hand on the center of the chest, between the nipples. Place other hand on top."),
            GuideStepEntity(0, "cpr_adult", 5, "Compressions", "Push hard and fast - 30 compressions at 100-120 per minute. Press down at least 2 inches deep."),
            GuideStepEntity(0, "cpr_adult", 6, "Open Airway", "Tilt the head back and lift the chin to open the airway."),
            GuideStepEntity(0, "cpr_adult", 7, "Rescue Breaths", "Give 2 breaths. Pinch nose, cover mouth, blow for 1 second each. Watch chest rise."),
            GuideStepEntity(0, "cpr_adult", 8, "Continue Cycles", "Repeat 30 compressions and 2 breaths until help arrives or person starts breathing."),
            
            // CPR Child Steps
            GuideStepEntity(0, "cpr_child", 1, "Check Responsiveness", "Tap the child and shout. Check for breathing. Stay calm."),
            GuideStepEntity(0, "cpr_child", 2, "Call for Help", "Shout for help. If someone is present, have them call 119."),
            GuideStepEntity(0, "cpr_child", 3, "Perform CPR First", "If alone, perform CPR for 2 minutes before calling 119."),
            GuideStepEntity(0, "cpr_child", 4, "Position Child", "Place child on firm, flat surface. Expose chest."),
            GuideStepEntity(0, "cpr_child", 5, "Hand Position", "Use one or two hands on center of chest, just below nipple line."),
            GuideStepEntity(0, "cpr_child", 6, "Compressions", "Push down about 2 inches at rate of 100-120 per minute. Give 30 compressions."),
            GuideStepEntity(0, "cpr_child", 7, "Open Airway", "Gently tilt head back and lift chin to open airway."),
            GuideStepEntity(0, "cpr_child", 8, "Rescue Breaths", "Give 2 gentle breaths. Cover child's mouth with yours. Watch chest rise."),
            GuideStepEntity(0, "cpr_child", 9, "Continue", "Repeat 30 compressions and 2 breaths until help arrives."),
            
            // Choking Steps
            GuideStepEntity(0, "choking", 1, "Assess Situation", "Ask 'Are you choking?' If person can speak or cough, encourage coughing."),
            GuideStepEntity(0, "choking", 2, "Position Yourself", "Stand behind the person. Wrap your arms around their waist."),
            GuideStepEntity(0, "choking", 3, "Make a Fist", "Make a fist with one hand. Place it just above the person's navel."),
            GuideStepEntity(0, "choking", 4, "Grasp Fist", "Grasp your fist with your other hand."),
            GuideStepEntity(0, "choking", 5, "Thrust Inward", "Give quick, upward thrusts into the abdomen. Use firm pressure."),
            GuideStepEntity(0, "choking", 6, "Repeat Thrusts", "Repeat thrusts until object is expelled or person can breathe."),
            GuideStepEntity(0, "choking", 7, "If Unconscious", "If person becomes unconscious, lower them to ground and start CPR."),
            
            // Snake Bite Steps
            GuideStepEntity(0, "snake_bite", 1, "Stay Calm", "Keep the victim and yourself calm. Panic increases heart rate and spreads venom faster."),
            GuideStepEntity(0, "snake_bite", 2, "Call Emergency", "Call 119 immediately. Try to identify the snake if safe to do so."),
            GuideStepEntity(0, "snake_bite", 3, "Position Victim", "Keep the bitten area below heart level if possible. Have victim lie down."),
            GuideStepEntity(0, "snake_bite", 4, "Remove Items", "Remove jewelry, watches, or tight clothing near the bite before swelling starts."),
            GuideStepEntity(0, "snake_bite", 5, "Keep Still", "Keep the victim as still as possible. Movement spreads venom through the body."),
            GuideStepEntity(0, "snake_bite", 6, "Clean Wound", "Gently clean the wound with soap and water if available."),
            GuideStepEntity(0, "snake_bite", 7, "Cover Bite", "Cover the bite with a clean, dry bandage or cloth."),
            GuideStepEntity(0, "snake_bite", 8, "Mark Swelling", "Use a pen to mark the edge of swelling. Check and mark every 15 minutes."),
            GuideStepEntity(0, "snake_bite", 9, "Monitor Victim", "Watch for symptoms: difficulty breathing, nausea, blurred vision, or excessive swelling."),
            
            // Bleeding Control Steps
            GuideStepEntity(0, "bleeding_control", 1, "Ensure Safety", "Make sure the area is safe. Wear gloves if available."),
            GuideStepEntity(0, "bleeding_control", 2, "Call Emergency", "Call 119 if bleeding is severe or doesn't stop."),
            GuideStepEntity(0, "bleeding_control", 3, "Apply Pressure", "Place clean cloth on wound. Apply firm, direct pressure for at least 10 minutes."),
            GuideStepEntity(0, "bleeding_control", 4, "Don't Remove Cloth", "If blood soaks through, add more cloth on top. Don't remove the first cloth."),
            GuideStepEntity(0, "bleeding_control", 5, "Elevate Limb", "If injury is on arm or leg, elevate it above heart level while maintaining pressure."),
            GuideStepEntity(0, "bleeding_control", 6, "Use Pressure Points", "If direct pressure isn't enough, press on pressure point between wound and heart."),
            GuideStepEntity(0, "bleeding_control", 7, "Apply Bandage", "Once bleeding stops, apply a sterile bandage. Don't tie too tight."),
            GuideStepEntity(0, "bleeding_control", 8, "Watch for Shock", "Keep person warm and comfortable. Monitor for pale skin, rapid pulse, weakness."),
            
            // Burns Steps
            GuideStepEntity(0, "burns", 1, "Stop Burning", "Remove person from heat source. Stop the burning process immediately."),
            GuideStepEntity(0, "burns", 2, "Remove Items", "Remove jewelry, clothing, and accessories near burn before swelling starts. Don't remove stuck clothing."),
            GuideStepEntity(0, "burns", 3, "Cool the Burn", "Run cool (not cold) water over burn for 10-20 minutes. Do not use ice."),
            GuideStepEntity(0, "burns", 4, "Assess Severity", "Check burn depth: First-degree (red), Second-degree (blisters), Third-degree (white/charred)."),
            GuideStepEntity(0, "burns", 5, "Cover Burn", "Cover with sterile, non-stick bandage or clean cloth. Don't use cotton."),
            GuideStepEntity(0, "burns", 6, "Don't Pop Blisters", "Never pop blisters. They protect against infection."),
            GuideStepEntity(0, "burns", 7, "Pain Relief", "Over-the-counter pain medication can help. Follow dosage instructions."),
            GuideStepEntity(0, "burns", 8, "Seek Medical Help", "Get medical attention for large burns, deep burns, or burns on sensitive areas."),
            
            // Fractures Steps
            GuideStepEntity(0, "fractures", 1, "Don't Move Victim", "Don't move the person unless necessary. Keep them still."),
            GuideStepEntity(0, "fractures", 2, "Call Emergency", "Call 119 for severe fractures, especially if bone is visible or suspected spinal injury."),
            GuideStepEntity(0, "fractures", 3, "Control Bleeding", "If there's bleeding, apply gentle pressure with clean cloth. Don't press on bone."),
            GuideStepEntity(0, "fractures", 4, "Immobilize Area", "Keep the injured area from moving. Support above and below the fracture site."),
            GuideStepEntity(0, "fractures", 5, "Apply Splint", "If trained, apply splint to immobilize. Use rigid materials like wood or rolled newspaper."),
            GuideStepEntity(0, "fractures", 6, "Ice the Area", "Apply ice wrapped in cloth to reduce swelling. Don't apply ice directly to skin."),
            GuideStepEntity(0, "fractures", 7, "Watch Circulation", "Check that area below injury is warm and has good color. Loosen splint if fingers/toes turn blue."),
            GuideStepEntity(0, "fractures", 8, "Treat for Shock", "Keep person warm and comfortable. Elevate legs slightly if no spinal injury."),
            
            // Heart Attack Steps
            GuideStepEntity(0, "heart_attack", 1, "Recognize Symptoms", "Chest pain, arm pain, shortness of breath, nausea, cold sweats, or lightheadedness."),
            GuideStepEntity(0, "heart_attack", 2, "Call 119 Immediately", "Don't wait. Call emergency services right away. Every minute counts."),
            GuideStepEntity(0, "heart_attack", 3, "Help Person Sit", "Have person sit down and rest. Keep them calm. Loosen tight clothing."),
            GuideStepEntity(0, "heart_attack", 4, "Aspirin", "If person is conscious and not allergic, give 1 adult aspirin (300mg) to chew slowly."),
            GuideStepEntity(0, "heart_attack", 5, "Nitroglycerin", "If person has prescribed nitroglycerin, help them take it as directed."),
            GuideStepEntity(0, "heart_attack", 6, "Monitor Person", "Stay with the person. Watch for changes in condition."),
            GuideStepEntity(0, "heart_attack", 7, "Be Ready for CPR", "If person becomes unconscious and stops breathing, begin CPR immediately."),
            
            // Stroke Steps
            GuideStepEntity(0, "stroke", 1, "F - Face", "Ask person to smile. Does one side of face droop?"),
            GuideStepEntity(0, "stroke", 2, "A - Arms", "Ask person to raise both arms. Does one arm drift downward?"),
            GuideStepEntity(0, "stroke", 3, "S - Speech", "Ask person to repeat a simple phrase. Is speech slurred or strange?"),
            GuideStepEntity(0, "stroke", 4, "T - Time", "If any of these signs are present, call 119 immediately. Note time symptoms started."),
            GuideStepEntity(0, "stroke", 5, "Call Emergency", "Call 119 even if symptoms seem minor or go away. Don't drive to hospital yourself."),
            GuideStepEntity(0, "stroke", 6, "Keep Person Calm", "Have person lie down with head and shoulders slightly elevated."),
            GuideStepEntity(0, "stroke", 7, "Don't Give Anything", "Don't give food, drink, or medication. Person may have trouble swallowing."),
            GuideStepEntity(0, "stroke", 8, "Monitor Vital Signs", "Watch breathing and consciousness. Be ready to perform CPR if needed."),
            
            // Drowning Steps
            GuideStepEntity(0, "drowning", 1, "Ensure Safety", "Only enter water if you're a strong swimmer and it's safe. Use reaching aid if possible."),
            GuideStepEntity(0, "drowning", 2, "Call for Help", "Shout for help. Have someone call 119 while you attempt rescue."),
            GuideStepEntity(0, "drowning", 3, "Reach or Throw", "Try to reach victim with pole, rope, or floatable object before entering water."),
            GuideStepEntity(0, "drowning", 4, "Remove from Water", "Get victim out of water as quickly and safely as possible."),
            GuideStepEntity(0, "drowning", 5, "Check Breathing", "Check if victim is breathing. Look, listen, and feel for breaths."),
            GuideStepEntity(0, "drowning", 6, "Begin CPR", "If not breathing, start CPR immediately. Give 2 rescue breaths, then 30 compressions."),
            GuideStepEntity(0, "drowning", 7, "Continue CPR", "Continue CPR until victim breathes, help arrives, or you're too exhausted to continue."),
            GuideStepEntity(0, "drowning", 8, "Recovery Position", "If victim is breathing, place in recovery position (on side) to prevent choking."),
            
            // Poisoning Steps
            GuideStepEntity(0, "poisoning", 1, "Identify Poison", "Try to identify what was ingested, inhaled, or touched. Save container if possible."),
            GuideStepEntity(0, "poisoning", 2, "Call Emergency", "Call 119 or poison control center immediately. Have poison information ready."),
            GuideStepEntity(0, "poisoning", 3, "Check Vital Signs", "Check breathing, pulse, and consciousness. Be ready to perform CPR."),
            GuideStepEntity(0, "poisoning", 4, "Remove from Source", "If poison was inhaled, move person to fresh air. If on skin, remove contaminated clothing."),
            GuideStepEntity(0, "poisoning", 5, "Don't Induce Vomiting", "Do not make person vomit unless instructed by poison control or medical professional."),
            GuideStepEntity(0, "poisoning", 6, "Rinse if Needed", "If poison is on skin or in eyes, rinse with water for at least 15 minutes."),
            GuideStepEntity(0, "poisoning", 7, "Keep Sample", "Save sample of poison or vomit for analysis. Keep container."),
            GuideStepEntity(0, "poisoning", 8, "Monitor Person", "Stay with person. Watch for symptoms like difficulty breathing, seizures, or unconsciousness.")
        )
    }
    
    fun getWarnings(): List<GuideWarningEntity> {
        return listOf(
            // CPR Adult Warnings
            GuideWarningEntity(0, "cpr_adult", "Do not stop CPR until professional help arrives or person starts breathing"),
            GuideWarningEntity(0, "cpr_adult", "If you're not trained, do hands-only CPR (compressions only, no breaths)"),
            GuideWarningEntity(0, "cpr_adult", "Do not be afraid to push hard during compressions - you may hear ribs crack"),
            
            // CPR Child Warnings
            GuideWarningEntity(0, "cpr_child", "Use less force than for adults but still compress chest adequately"),
            GuideWarningEntity(0, "cpr_child", "Do not delay CPR to call 119 if you're alone - do 2 minutes of CPR first"),
            GuideWarningEntity(0, "cpr_child", "Be gentle with rescue breaths - child's lungs are smaller"),
            
            // Choking Warnings
            GuideWarningEntity(0, "choking", "Do not perform Heimlich on a person who can cough or speak"),
            GuideWarningEntity(0, "choking", "Do not slap back if abdominal thrusts are working"),
            GuideWarningEntity(0, "choking", "Never attempt to retrieve object with fingers unless you can see it clearly"),
            
            // Snake Bite Warnings
            GuideWarningEntity(0, "snake_bite", "DO NOT cut the wound"),
            GuideWarningEntity(0, "snake_bite", "DO NOT try to suck out venom"),
            GuideWarningEntity(0, "snake_bite", "DO NOT apply ice or tourniquets"),
            GuideWarningEntity(0, "snake_bite", "DO NOT give alcohol or medications"),
            GuideWarningEntity(0, "snake_bite", "DO NOT try to catch or kill the snake"),
            
            // Bleeding Control Warnings
            GuideWarningEntity(0, "bleeding_control", "Do not remove embedded objects - stabilize them instead"),
            GuideWarningEntity(0, "bleeding_control", "Do not use tourniquets unless trained and bleeding is life-threatening"),
            GuideWarningEntity(0, "bleeding_control", "Do not peek at wound repeatedly - maintain constant pressure"),
            
            // Burns Warnings
            GuideWarningEntity(0, "burns", "Do not use ice - it can cause further damage"),
            GuideWarningEntity(0, "burns", "Do not apply butter, oil, or ointments to serious burns"),
            GuideWarningEntity(0, "burns", "Do not pop blisters - they protect against infection"),
            GuideWarningEntity(0, "burns", "Do not remove stuck clothing - cut around it"),
            
            // Fractures Warnings
            GuideWarningEntity(0, "fractures", "Do not try to realign the bone"),
            GuideWarningEntity(0, "fractures", "Do not move person if spinal injury is suspected"),
            GuideWarningEntity(0, "fractures", "Do not give anything by mouth if surgery might be needed"),
            
            // Heart Attack Warnings
            GuideWarningEntity(0, "heart_attack", "Do not wait to see if symptoms go away"),
            GuideWarningEntity(0, "heart_attack", "Do not drive person to hospital - wait for ambulance"),
            GuideWarningEntity(0, "heart_attack", "Do not give aspirin if person has aspirin allergy or bleeding disorder"),
            GuideWarningEntity(0, "heart_attack", "Do not leave person alone"),
            
            // Stroke Warnings
            GuideWarningEntity(0, "stroke", "Do not wait to call 119 even if symptoms seem minor"),
            GuideWarningEntity(0, "stroke", "Do not give aspirin or other medication"),
            GuideWarningEntity(0, "stroke", "Do not give food or drink - swallowing may be impaired"),
            GuideWarningEntity(0, "stroke", "Do not drive to hospital - ambulance is faster and has equipment"),
            
            // Drowning Warnings
            GuideWarningEntity(0, "drowning", "Do not enter water unless you're a strong swimmer"),
            GuideWarningEntity(0, "drowning", "Do not turn away from victim during CPR"),
            GuideWarningEntity(0, "drowning", "Even if victim seems okay, seek medical attention for secondary drowning"),
            
            // Poisoning Warnings
            GuideWarningEntity(0, "poisoning", "Do not induce vomiting unless specifically instructed"),
            GuideWarningEntity(0, "poisoning", "Do not give activated charcoal unless instructed by poison control"),
            GuideWarningEntity(0, "poisoning", "Do not try to neutralize poison with other substances"),
            GuideWarningEntity(0, "poisoning", "Do not give anything by mouth to unconscious person")
        )
    }
}
