import SwiftUI
import shared
@main
struct iOSApp: App {
    init() {
        KoinIosKt.doInitKoinForIos()
    }
    
    var body: some Scene {
        WindowGroup {
            ListView()
        }
    }
}
