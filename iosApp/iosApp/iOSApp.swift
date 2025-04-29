import SwiftUI
import composeApp

@main
struct iOSApp: App {
    init() {
        KoinIosKt.doInitKoinForIos()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
