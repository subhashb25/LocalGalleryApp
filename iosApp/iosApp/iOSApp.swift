import SwiftUI
import composeApp

@main
struct iOSApp: App {
    init() {
        print("Initializing Koin for iOS...")
        KoinIosKt.doInitKoinForIos()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
